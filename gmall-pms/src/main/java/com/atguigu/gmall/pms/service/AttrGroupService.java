package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.vo.AttrGroupVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 属性分组
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 17:37:50
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPage(QueryCondition params,Long catId);


    AttrGroupVo queryAttr(Long gid);

    List<AttrGroupVo> queryGroup(Long catId);
}

