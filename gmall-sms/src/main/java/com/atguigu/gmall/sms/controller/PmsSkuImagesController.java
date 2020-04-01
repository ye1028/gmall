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

import com.atguigu.gmall.sms.entity.PmsSkuImagesEntity;
import com.atguigu.gmall.sms.service.PmsSkuImagesService;




/**
 * sku图片
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "sku图片 管理")
@RestController
@RequestMapping("sms/pmsskuimages")
public class PmsSkuImagesController {
    @Autowired
    private PmsSkuImagesService pmsSkuImagesService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmsskuimages:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsSkuImagesService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:pmsskuimages:info')")
    public Resp<PmsSkuImagesEntity> info(@PathVariable("id") Long id){
		PmsSkuImagesEntity pmsSkuImages = pmsSkuImagesService.getById(id);

        return Resp.ok(pmsSkuImages);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmsskuimages:save')")
    public Resp<Object> save(@RequestBody PmsSkuImagesEntity pmsSkuImages){
		pmsSkuImagesService.save(pmsSkuImages);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmsskuimages:update')")
    public Resp<Object> update(@RequestBody PmsSkuImagesEntity pmsSkuImages){
		pmsSkuImagesService.updateById(pmsSkuImages);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmsskuimages:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		pmsSkuImagesService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
