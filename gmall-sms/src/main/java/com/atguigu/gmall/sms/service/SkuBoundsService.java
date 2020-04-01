package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.vo.YxVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.SkuBoundsEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 商品sku积分设置
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-18 16:25:47
 */
public interface SkuBoundsService extends IService<SkuBoundsEntity> {

    PageVo queryPage(QueryCondition params);

    void saveSale(YxVo yxVo);
}

