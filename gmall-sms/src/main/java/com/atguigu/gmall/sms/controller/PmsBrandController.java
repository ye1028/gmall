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

import com.atguigu.gmall.sms.entity.PmsBrandEntity;
import com.atguigu.gmall.sms.service.PmsBrandService;




/**
 * 品牌
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "品牌 管理")
@RestController
@RequestMapping("sms/pmsbrand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsbrand:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsBrandService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{brandId}")
    @PreAuthorize("hasAuthority('sms:pmsbrand:info')")
    public Resp<PmsBrandEntity> info(@PathVariable("brandId") Long brandId){
		PmsBrandEntity pmsBrand = pmsBrandService.getById(brandId);

        return Resp.ok(pmsBrand);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsbrand:save')")
    public Resp<Object> save(@RequestBody PmsBrandEntity pmsBrand){
		pmsBrandService.save(pmsBrand);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsbrand:update')")
    public Resp<Object> update(@RequestBody PmsBrandEntity pmsBrand){
		pmsBrandService.updateById(pmsBrand);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsbrand:delete')")
    public Resp<Object> delete(@RequestBody Long[] brandIds){
		pmsBrandService.removeByIds(Arrays.asList(brandIds));

        return Resp.ok(null);
    }

}
