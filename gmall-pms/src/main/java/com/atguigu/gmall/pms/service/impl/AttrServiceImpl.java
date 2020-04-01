package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.vo.AttrVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPage(QueryCondition params, Long cid, Integer type) {
        QueryWrapper<AttrEntity> q = new QueryWrapper<>();
        if(cid != null){
            q.eq("catelog_id", cid);
        }
        q.eq("attr_type", type);
        IPage<AttrEntity> iPage = this.page(new Query<AttrEntity>().getPage(params),
                q
        );
        return new PageVo(iPage);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        this.save(attr);
        Long attrId = attr.getAttrId();
        AttrAttrgroupRelationEntity a = new AttrAttrgroupRelationEntity();
        System.out.println(attr.getAttrGroupId());
        a.setAttrGroupId(attr.getAttrGroupId());
        a.setAttrId(attrId);
        attrAttrgroupRelationDao.insert(a);
    }

}