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

import com.atguigu.gmall.sms.entity.PmsProductAttrValueEntity;
import com.atguigu.gmall.sms.service.PmsProductAttrValueService;




/**
 * spu属性值
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "spu属性值 管理")
@RestController
@RequestMapping("sms/pmsproductattrvalue")
public class PmsProductAttrValueController {
    @Autowired
    private PmsProductAttrValueService pmsProductAttrValueService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsproductattrvalue:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsProductAttrValueService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:pmsproductattrvalue:info')")
    public Resp<PmsProductAttrValueEntity> info(@PathVariable("id") Long id){
		PmsProductAttrValueEntity pmsProductAttrValue = pmsProductAttrValueService.getById(id);

        return Resp.ok(pmsProductAttrValue);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsproductattrvalue:save')")
    public Resp<Object> save(@RequestBody PmsProductAttrValueEntity pmsProductAttrValue){
		pmsProductAttrValueService.save(pmsProductAttrValue);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsproductattrvalue:update')")
    public Resp<Object> update(@RequestBody PmsProductAttrValueEntity pmsProductAttrValue){
		pmsProductAttrValueService.updateById(pmsProductAttrValue);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsproductattrvalue:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		pmsProductAttrValueService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
