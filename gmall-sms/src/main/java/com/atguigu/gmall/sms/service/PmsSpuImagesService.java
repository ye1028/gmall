package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.PmsSpuImagesEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * spu图片
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 19:52:33
 */
public interface PmsSpuImagesService extends IService<PmsSpuImagesEntity> {

    PageVo queryPage(QueryCondition params);
}

