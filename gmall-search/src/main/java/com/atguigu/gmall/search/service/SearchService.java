package com.atguigu.gmall.search.service;

import com.atguigu.gmall.search.pojo.SearchParam;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void search(SearchParam searchParam) throws IOException {
        SearchRequest searchRequest = buildQuery(searchParam);
        restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    }

    private SearchRequest buildQuery(SearchParam searchParam) {
        String keyword = searchParam.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        //查询构造器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND));

        //构建过滤条件
        String[] brand = searchParam.getBrand();
        if (brand != null && brand.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", brand));
        }
        //构建分类过滤条件
        String[] catelog3 = searchParam.getCatelog3();
        if (catelog3 != null && catelog3.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("categoryId", catelog3));
        }
        //构建规格属性
        String[] props = searchParam.getProps();
        if (props != null && props.length != 0) {
            for (String prop : props) {
                BoolQueryBuilder QueryBuilder = QueryBuilders.boolQuery();
                BoolQueryBuilder subBuilder = QueryBuilders.boolQuery();
                String[] split = StringUtils.split(prop, ":");
                if (split != null || split.length != 2) {
                    continue;
                }
                String[] attrValue = StringUtils.split(split[1], "-");
                subBuilder.must(QueryBuilders.termQuery("attrs.attrId", split[0]));
                subBuilder.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));
                QueryBuilder.must(QueryBuilders.nestedQuery("attrs", subBuilder, ScoreMode.None));
                boolQueryBuilder.filter(QueryBuilder);
            }
        }
        sourceBuilder.query(boolQueryBuilder);

        //构建分页
        sourceBuilder.from(searchParam.getPageNum());
        sourceBuilder.size(searchParam.getPageSize());

        //构建排序
        String order = searchParam.getOrder();
        if (!StringUtils.isEmpty(order)) {
            String[] split = StringUtils.split(order, ":");
            if (split != null && split.length == 2) {
                String field = null;
                switch (split[0]) {
                    case "1": field="sale";
                        break;
                    case "2": field ="price";
                        break;
                }
                sourceBuilder.sort(field,StringUtils.equals("asc",split[1])? SortOrder.ASC:SortOrder.DESC);
            }
        }

        //构建高亮
        sourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));
        //构建聚合
        TermsAggregationBuilder terms = AggregationBuilders.terms("brandAgg").field("brandId").subAggregation(AggregationBuilders.terms("BrandNameAgg").field("brandName"));
        sourceBuilder.aggregation(terms);
        //分类聚合
        TermsAggregationBuilder terms1 = AggregationBuilders.terms("cateAgg").field("categoryId").subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName"));
        sourceBuilder.aggregation(terms1);
        //搜索规格属性聚合
        sourceBuilder.aggregation(AggregationBuilders.nested("attrAgg","attrs")
        .subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
         .subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
        .subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue")))
        );
        System.out.println(sourceBuilder.toString());
        //查询参数
        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.types("info");
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }
}
