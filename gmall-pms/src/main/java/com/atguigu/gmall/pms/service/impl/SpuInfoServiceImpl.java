package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.controller.feign.GmallSmsClient;
import com.atguigu.gmall.pms.dao.SkuInfoDao;
import com.atguigu.gmall.pms.dao.SpuInfoDescDao;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.entity.vo.*;
import com.atguigu.gmall.pms.service.ProductAttrValueService;
import com.atguigu.gmall.pms.service.SkuSaleAttrValueService;
import com.atguigu.gmall.sms.vo.YxVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.SpuInfoDao;
import com.atguigu.gmall.pms.service.SpuInfoService;
import org.springframework.util.CollectionUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private GmallSmsClient gmallSmsClient;

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private ProductAttrValueService attrValueService;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPage(QueryCondition queryCondition, Long catId) {
        QueryWrapper<SpuInfoEntity> q = new QueryWrapper<>();
        if (catId != 0) {
            q.eq("catalog_id", catId);
        }
        String key = queryCondition.getKey();
        if (StringUtils.isNotEmpty(queryCondition.getKey())) {
            q.and(t -> t.eq("id", key).or().like("spu_name", key)
            );
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(queryCondition),
                q
        );
        return new PageVo(page);
    }

    @Override
    @GlobalTransactional
    public void bigSave(SpuInfoVo spuInfo) {
        // 1.保存spu相关的三张表
        //1.1保存spu
        spuInfo.setCreateTime(new Date());
        spuInfo.setUodateTime(spuInfo.getCreateTime());
        this.save(spuInfo);
        Long id = spuInfo.getId();
        //1.2 保存spu描述信息
        List<String> spuImages = spuInfo.getSpuImages();
        spuImages.add(0, "aaaaa");
        if (!CollectionUtils.isEmpty(spuImages)) {

            SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
            spuInfoDescEntity.setSpuId(id);
            spuInfoDescEntity.setDecript(StringUtils.join(spuImages, ","));
            spuInfoDescDao.insert(spuInfoDescEntity);
        }
        //1.3 保存pms_product_attr_value
        List<BaseAttrVo> baseAttrVos = spuInfo.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrVos)) {
            List<ProductAttrValueEntity> collect = baseAttrVos.stream().map(a -> {
                        ProductAttrValueEntity attrValueEntity = a;
                        attrValueEntity.setSpuId(id);
                        return attrValueEntity;
                    }
            ).collect(Collectors.toList());
            attrValueService.saveBatch(collect);
        }
        ;


        // 2.保存sku相关的三张表
        List<SkuVo> skus = spuInfo.getSkus();
        skus.forEach(sku -> {
            // 2.1 保存sku_info
            sku.setSpuId(id);
            sku.setSkuCode(UUID.randomUUID().toString());
            sku.setBrandId(spuInfo.getBrandId());
            sku.setCatalogId(spuInfo.getCatalogId());
            skuInfoDao.insert(sku);

            List<SkuSaleAttrValueEntity> saleAttrs = sku.getSaleAttrs();
            if (!CollectionUtils.isEmpty(saleAttrs)) {
                saleAttrs.forEach(attr -> {
                    attr.setSkuId(id);
                });
                skuSaleAttrValueService.saveBatch(saleAttrs);
            }

            YxVo y = new YxVo();
            BeanUtils.copyProperties(sku, y);
            y.setSkuId(spuInfo.getId());
            // 3.保存折扣的几张表
            System.out.println(y);
            gmallSmsClient.saveSale(y);
        });
                 sendMsg("insert",id);

    }

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${rabbitmq.exchange}")
    private String EXCHANGE;

    private void sendMsg(String type,Long spuId){
        amqpTemplate.convertAndSend(EXCHANGE,"item."+type,spuId);
    }
}