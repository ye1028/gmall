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

import com.atguigu.gmall.sms.entity.PmsCommentReplayEntity;
import com.atguigu.gmall.sms.service.PmsCommentReplayService;




/**
 * 商品评价回复关系
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
@Api(tags = "商品评价回复关系 管理")
@RestController
@RequestMapping("sms/pmscommentreplay")
public class PmsCommentReplayController {
    @Autowired
    private PmsCommentReplayService pmsCommentReplayService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:pmscommentreplay:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = pmsCommentReplayService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:pmscommentreplay:info')")
    public Resp<PmsCommentReplayEntity> info(@PathVariable("id") Long id){
		PmsCommentReplayEntity pmsCommentReplay = pmsCommentReplayService.getById(id);

        return Resp.ok(pmsCommentReplay);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:pmscommentreplay:save')")
    public Resp<Object> save(@RequestBody PmsCommentReplayEntity pmsCommentReplay){
		pmsCommentReplayService.save(pmsCommentReplay);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:pmscommentreplay:update')")
    public Resp<Object> update(@RequestBody PmsCommentReplayEntity pmsCommentReplay){
		pmsCommentReplayService.updateById(pmsCommentReplay);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:pmscommentreplay:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		pmsCommentReplayService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
