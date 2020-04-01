package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.entity.vo.CategoryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GmallPmsApi {
    @PostMapping("/pms/spuinfo/page")
    public Resp<List<SpuInfoEntity>>  info1(QueryCondition queryCondition);

    @GetMapping("/pms/skuinfo/{spuId}")
    public Resp<List<SkuInfoEntity>> selectSku
            (
                    @PathVariable("spuId") Long spuid
            );

    @GetMapping("/pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    @GetMapping("pms/category/info/{catId}")
    public Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<ProductAttrValueEntity>> querySearchAttrValue(@PathVariable("spuId") Long spuId);


    @GetMapping("pms/spuinfo/info/{id}")
    public Resp<SpuInfoEntity> querySpuById(@PathVariable("id") Long id) ;

    @GetMapping("pms/category")
    public Resp<List<CategoryEntity>> queryCate(@RequestParam(value = "level", defaultValue = "0") Integer level, @RequestParam(value = "parentCid", required = false) Long pid) ;

    @GetMapping("pms/category/{pid}")
    public Resp<List<CategoryVo>> querySubCate(@PathVariable("pid") Long pid) ;
}
