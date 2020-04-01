package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 商品三级分类
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 17:37:50
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageVo queryPage(QueryCondition params);

    List<CategoryVo> querySubCate(Long pid);
}

