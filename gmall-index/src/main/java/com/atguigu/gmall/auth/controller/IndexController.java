package com.atguigu.gmall.auth.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.vo.CategoryVo;
import com.atguigu.gmall.auth.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/cates")
    public Resp<List<CategoryEntity>> queryCategory() {
        List<CategoryEntity> ls = indexService.queryCategory();
        return Resp.ok(ls);
    }

    @GetMapping("/cates/{pid}")
    public Resp<List<CategoryVo>> quertCategory2(@PathVariable("pid") Long pid) {
        List<CategoryVo> ls = indexService.quertCategory2(pid);
        return Resp.ok(ls);
    }
}
