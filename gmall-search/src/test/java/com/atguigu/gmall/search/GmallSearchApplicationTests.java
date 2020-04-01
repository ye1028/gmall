package com.atguigu.gmall.search;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.api.GmallPmsApi;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsApi;
import com.atguigu.gmall.search.pojo.Goods;
import com.atguigu.gmall.search.pojo.SearchAttr;
import com.atguigu.gmall.search.respository.GoodRespository;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class GmallSearchApplicationTests {
    @Autowired
    private GoodRespository goodRespository;

    @Autowired
    private ElasticsearchRestTemplate repository;

    @Autowired
    private GmallWmsApi gmallWmsApi;

    @Autowired
    private GmallPmsClient gmallPmsApi;

    @Test
    void contextLoads() {
        repository.createIndex(Goods.class);
        repository.putMapping(Goods.class);
    }

    @Test
    void test01() {

    }


    @Test
    void test() {
        Long pageNumb = 1L;
        Long pageSize = 100L;
        do {
            //分页查询spu
            QueryCondition queryCondition = new QueryCondition();
            queryCondition.setPage(pageNumb);
            queryCondition.setLimit(pageSize);
            Resp<List<SpuInfoEntity>> list = gmallPmsApi.info1(queryCondition);
            List<SpuInfoEntity> spu = list.getData();
            //遍历sku
            spu.forEach(spuI -> {
                Resp<List<SkuInfoEntity>> skuResp = gmallPmsApi.selectSku(spuI.getId());
                List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
                if (!CollectionUtils.isEmpty(skuInfoEntities)) {
                    List<Goods> goodlist = skuInfoEntities.stream().map(skuInfoEntity -> {
                        Goods goods = new Goods();
                        //查询搜索属性
                        Resp<List<ProductAttrValueEntity>> listResp1 = gmallPmsApi.querySearchAttrValue(spuI.getId());
                        List<ProductAttrValueEntity> data1 = listResp1.getData();
                        if (!CollectionUtils.isEmpty(data1)) {
                            List<SearchAttr> collect = data1.stream().map(attr -> {
                                SearchAttr searchAttr = new SearchAttr();
                                searchAttr.setAttrId(attr.getAttrId());
                                searchAttr.setAttrName(attr.getAttrName());
                                searchAttr.setAttrValue(attr.getAttrValue());
                                return searchAttr;
                            }).collect(Collectors.toList());
                            goods.setAttrs(collect);
                        }
                        //查询品牌
                        Resp<BrandEntity> brandEntityResp = this.gmallPmsApi.queryBrandById(skuInfoEntity.getBrandId());
                        BrandEntity data2 = brandEntityResp.getData();
                        if (data2 != null) {
                            goods.setBrandId(skuInfoEntity.getBrandId());
                            goods.setBrandName(data2.getName());
                        }
                        //查询分类
                        Resp<CategoryEntity> categoryEntityResp = gmallPmsApi.queryCategoryById(skuInfoEntity.getCatalogId());
                        CategoryEntity cate = categoryEntityResp.getData();
                        if (cate != null) {
                            goods.setCategoryId(skuInfoEntity.getCatalogId());
                            goods.setCategoryName(cate.getName());
                        }
                        goods.setCreateTime(spuI.getCreateTime());
                        goods.setPic(skuInfoEntity.getSkuDefaultImg());
                        goods.setPrice(skuInfoEntity.getPrice().doubleValue());
                        goods.setSale(0l);
                        goods.setSkuId(skuInfoEntity.getSkuId());
                        //查询库存
                        ////                                Resp<List<WareSkuEntity>> listResp2 = gmallWmsApi.selectWareSku(skuInfoEntity.getSkuId());
                        ////                                List<WareSkuEntity> data3 = listResp2.getData();
                        ////                                boolean b = data3.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0);
                        ////                                goods.setStore(b);

                        goods.setTitle(skuInfoEntity.getSkuTitle());
                        return goods;
                    }).collect(Collectors.toList());
                    this.goodRespository.saveAll(goodlist);
                }


            });


            //把sku转换成good对象】

            //导入索引库

            pageSize = (long) spu.size();
            pageNumb++;
        } while (pageSize == 100);
    }

}
