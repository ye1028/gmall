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

import com.atguigu.gmall.sms.entity.PmsSpuImagesEntity;
import com.atguigu.gmall.sms.service.PmsSpuImagesService;




/**
 * spu图片
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "spu图片 管理")
@RestController
@RequestMapping("sms/pmsspuimages")
public class PmsSpuImagesController {
    @Autowired
    private PmsSpuImagesService pmsSpuImagesService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsspuimages:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsSpuImagesService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:pmsspuimages:info')")
    public Resp<PmsSpuImagesEntity> info(@PathVariable("id") Long id){
		PmsSpuImagesEntity pmsSpuImages = pmsSpuImagesService.getById(id);

        return Resp.ok(pmsSpuImages);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsspuimages:save')")
    public Resp<Object> save(@RequestBody PmsSpuImagesEntity pmsSpuImages){
		pmsSpuImagesService.save(pmsSpuImages);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsspuimages:update')")
    public Resp<Object> update(@RequestBody PmsSpuImagesEntity pmsSpuImages){
		pmsSpuImagesService.updateById(pmsSpuImages);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsspuimages:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		pmsSpuImagesService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
