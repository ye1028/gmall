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

import com.atguigu.gmall.sms.entity.PmsAttrGroupEntity;
import com.atguigu.gmall.sms.service.PmsAttrGroupService;




/**
 * 属性分组
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "属性分组 管理")
@RestController
@RequestMapping("sms/pmsattrgroup")
public class PmsAttrGroupController {
    @Autowired
    private PmsAttrGroupService pmsAttrGroupService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsattrgroup:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsAttrGroupService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{attrGroupId}")
    @PreAuthorize("hasAuthority('sms:pmsattrgroup:info')")
    public Resp<PmsAttrGroupEntity> info(@PathVariable("attrGroupId") Long attrGroupId){
		PmsAttrGroupEntity pmsAttrGroup = pmsAttrGroupService.getById(attrGroupId);

        return Resp.ok(pmsAttrGroup);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsattrgroup:save')")
    public Resp<Object> save(@RequestBody PmsAttrGroupEntity pmsAttrGroup){
		pmsAttrGroupService.save(pmsAttrGroup);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsattrgroup:update')")
    public Resp<Object> update(@RequestBody PmsAttrGroupEntity pmsAttrGroup){
		pmsAttrGroupService.updateById(pmsAttrGroup);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsattrgroup:delete')")
    public Resp<Object> delete(@RequestBody Long[] attrGroupIds){
		pmsAttrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return Resp.ok(null);
    }

}
