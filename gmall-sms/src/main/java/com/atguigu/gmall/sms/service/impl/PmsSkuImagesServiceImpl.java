package com.atguigu.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.sms.dao.PmsSkuImagesDao;
import com.atguigu.gmall.sms.entity.PmsSkuImagesEntity;
import com.atguigu.gmall.sms.service.PmsSkuImagesService;


@Service("pmsSkuImagesService")
public class PmsSkuImagesServiceImpl extends ServiceImpl<PmsSkuImagesDao, PmsSkuImagesEntity> implements PmsSkuImagesService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<PmsSkuImagesEntity> page = this.page(
                new Query<PmsSkuImagesEntity>().getPage(params),
                new QueryWrapper<PmsSkuImagesEntity>()
        );

        return new PageVo(page);
    }

}