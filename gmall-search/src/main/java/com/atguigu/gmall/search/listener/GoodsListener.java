package com.atguigu.gmall.search.listener;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.search.feign.GmallPmsClient;
import com.atguigu.gmall.search.feign.GmallWmsApi;
import com.atguigu.gmall.search.pojo.Goods;
import com.atguigu.gmall.search.pojo.SearchAttr;
import com.atguigu.gmall.search.respository.GoodRespository;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoodsListener {
@Autowired
private GoodRespository goodRespository;

    @Autowired
    private GmallWmsApi gmallWmsApi;

    @Autowired
    private GmallPmsClient gmallPmsApi;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value="gmall-search-queue",durable ="true" ),
                    exchange = @Exchange(value = "gmall-pms-exchange",type = ExchangeTypes.TOPIC),
                    key = {"item.insert","item.update"}
            )
    )
    public void listener(Long spuId){
        Resp<List<SkuInfoEntity>> skuResp = gmallPmsApi.selectSku(spuId);
        List<SkuInfoEntity> skuInfoEntities = skuResp.getData();
        if (!CollectionUtils.isEmpty(skuInfoEntities)) {
            List<Goods> goodlist = skuInfoEntities.stream().map(skuInfoEntity -> {
                Goods goods = new Goods();
                //查询搜索属性
                Resp<List<ProductAttrValueEntity>> listResp1 = gmallPmsApi.querySearchAttrValue(spuId);
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
                Resp<SpuInfoEntity> spuInfoEntityResp = gmallPmsApi.querySpuById(spuId);
                SpuInfoEntity data = spuInfoEntityResp.getData();
                goods.setCreateTime(data.getCreateTime());
                goods.setPic(skuInfoEntity.getSkuDefaultImg());
                goods.setPrice(skuInfoEntity.getPrice().doubleValue());
                goods.setSale(0l);
                goods.setSkuId(skuInfoEntity.getSkuId());

                goods.setTitle(skuInfoEntity.getSkuTitle());
                return goods;
            }).collect(Collectors.toList());
            this.goodRespository.saveAll(goodlist);
        }
       }
}

