package com.atguigu.gmall.item.vo;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuImagesEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;

import java.math.BigDecimal;
import java.util.List;

public class ItemVo {
    private Long skuId;
    private CategoryEntity categoryEntity;
    private String spuName;
    private Long spuId;
    private String skuTitle;
    private BigDecimal price;
    private BigDecimal weight;

    private List<SkuImagesEntity> skuImages;


}
