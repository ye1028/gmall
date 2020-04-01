package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品属性
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 17:37:50
 */
public interface AttrService extends IService<AttrEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPage(QueryCondition params,Long cid,Integer type);

    void saveAttr(AttrVo attr);
}

