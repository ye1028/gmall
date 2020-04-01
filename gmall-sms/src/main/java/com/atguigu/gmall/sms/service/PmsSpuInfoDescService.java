package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.PmsSpuInfoDescEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * spu信息介绍
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
public interface PmsSpuInfoDescService extends IService<PmsSpuInfoDescEntity> {

    PageVo queryPage(QueryCondition params);
}

