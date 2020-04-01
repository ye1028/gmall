package com.atguigu.gmall.sms.controller;

import java.util.Arrays;
import java.util.Map;


import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gmall.sms.entity.PmsSkuInfoEntity;
import com.atguigu.gmall.sms.service.PmsSkuInfoService;




/**
 * sku信息
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "sku信息 管理")
@RestController
@RequestMapping("sms/pmsskuinfo")
public class PmsSkuInfoController {
    @Autowired
    private PmsSkuInfoService pmsSkuInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsskuinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsSkuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{skuId}")
    @PreAuthorize("hasAuthority('sms:pmsskuinfo:info')")
    public Resp<PmsSkuInfoEntity> info(@PathVariable("skuId") Long skuId){
		PmsSkuInfoEntity pmsSkuInfo = pmsSkuInfoService.getById(skuId);

        return Resp.ok(pmsSkuInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsskuinfo:save')")
    public Resp<Object> save(@RequestBody PmsSkuInfoEntity pmsSkuInfo){
		pmsSkuInfoService.save(pmsSkuInfo);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsskuinfo:update')")
    public Resp<Object> update(@RequestBody PmsSkuInfoEntity pmsSkuInfo){
		pmsSkuInfoService.updateById(pmsSkuInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsskuinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] skuIds){
		pmsSkuInfoService.removeByIds(Arrays.asList(skuIds));

        return Resp.ok(null);
    }

}
