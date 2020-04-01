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

import com.atguigu.gmall.sms.entity.PmsSpuInfoEntity;
import com.atguigu.gmall.sms.service.PmsSpuInfoService;




/**
 * spu信息
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "spu信息 管理")
@RestController
@RequestMapping("sms/pmsspuinfo")
public class PmsSpuInfoController {
    @Autowired
    private PmsSpuInfoService pmsSpuInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsspuinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsSpuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:pmsspuinfo:info')")
    public Resp<PmsSpuInfoEntity> info(@PathVariable("id") Long id){
		PmsSpuInfoEntity pmsSpuInfo = pmsSpuInfoService.getById(id);

        return Resp.ok(pmsSpuInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsspuinfo:save')")
    public Resp<Object> save(@RequestBody PmsSpuInfoEntity pmsSpuInfo){
		pmsSpuInfoService.save(pmsSpuInfo);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsspuinfo:update')")
    public Resp<Object> update(@RequestBody PmsSpuInfoEntity pmsSpuInfo){
		pmsSpuInfoService.updateById(pmsSpuInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsspuinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		pmsSpuInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
