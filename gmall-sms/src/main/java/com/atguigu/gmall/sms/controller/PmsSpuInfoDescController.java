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

import com.atguigu.gmall.sms.entity.PmsSpuInfoDescEntity;
import com.atguigu.gmall.sms.service.PmsSpuInfoDescService;




/**
 * spu信息介绍
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "spu信息介绍 管理")
@RestController
@RequestMapping("sms/pmsspuinfodesc")
public class PmsSpuInfoDescController {
    @Autowired
    private PmsSpuInfoDescService pmsSpuInfoDescService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsspuinfodesc:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsSpuInfoDescService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{spuId}")
    @PreAuthorize("hasAuthority('sms:pmsspuinfodesc:info')")
    public Resp<PmsSpuInfoDescEntity> info(@PathVariable("spuId") Long spuId){
		PmsSpuInfoDescEntity pmsSpuInfoDesc = pmsSpuInfoDescService.getById(spuId);

        return Resp.ok(pmsSpuInfoDesc);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsspuinfodesc:save')")
    public Resp<Object> save(@RequestBody PmsSpuInfoDescEntity pmsSpuInfoDesc){
		pmsSpuInfoDescService.save(pmsSpuInfoDesc);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsspuinfodesc:update')")
    public Resp<Object> update(@RequestBody PmsSpuInfoDescEntity pmsSpuInfoDesc){
		pmsSpuInfoDescService.updateById(pmsSpuInfoDesc);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsspuinfodesc:delete')")
    public Resp<Object> delete(@RequestBody Long[] spuIds){
		pmsSpuInfoDescService.removeByIds(Arrays.asList(spuIds));

        return Resp.ok(null);
    }

}
