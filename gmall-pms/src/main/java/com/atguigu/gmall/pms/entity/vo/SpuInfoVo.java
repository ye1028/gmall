package com.atguigu.gmall.pms.entity.vo;

import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class SpuInfoVo extends SpuInfoEntity {
    private List<String> spuImages;
    private List<BaseAttrVo> baseAttrs;
    private List<SkuVo> skus;
}
