package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrDao attrDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPage(QueryCondition params, Long catId) {
        QueryWrapper<AttrGroupEntity> q = new QueryWrapper<>();
        if (q != null) {
            q.eq("catelog_id", catId);
        }
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                q
        );
        return new PageVo(page);
    }

    @Override
    public AttrGroupVo queryAttr(Long gid) {
        AttrGroupVo attrGroupVo = new AttrGroupVo();
        AttrGroupEntity byId = this.getById(gid);
        BeanUtils.copyProperties(byId, attrGroupVo);
        List<AttrAttrgroupRelationEntity> attr_group_id = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        if (CollectionUtils.isEmpty(attr_group_id)) {
            return attrGroupVo;
        }
        attrGroupVo.setRelations(attr_group_id);
        List<Long> collect = attr_group_id.stream().map(relationEntity -> relationEntity.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = attrDao.selectBatchIds(collect);
        attrGroupVo.setAttrEntities(attrEntities);
        return attrGroupVo;
    }

    @Override
    public List<AttrGroupVo> queryGroup(Long catId) {
        List<AttrGroupVo> attrGroupVo = new ArrayList<>();
        List<AttrGroupEntity> catelog_id = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        List<AttrGroupVo> groupId = catelog_id.stream().map(attr ->
                queryAttr(attr.getAttrGroupId())
        ).collect(Collectors.toList());

        return groupId;
    }


}