package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.vo.AttrGroupVo;
import com.atguigu.gmall.pms.entity.vo.SpuInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * spu信息
 *
 * @author y
 * @email lxf@atguigu.com
 * @date 2020-03-15 17:37:49
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo queryPage(QueryCondition queryCondition, Long catId);

    void bigSave(SpuInfoVo spuInfo);
}

