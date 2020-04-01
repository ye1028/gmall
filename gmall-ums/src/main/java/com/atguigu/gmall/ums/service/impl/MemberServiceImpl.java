package com.atguigu.gmall.ums.service.impl;

import com.atguigu.core.exception.MemberException;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.ums.dao.MemberDao;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.gmall.ums.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public Boolean checkdata(String data, Integer type) {
        QueryWrapper<MemberEntity> q = new QueryWrapper<>();
        switch (type) {
            case 1:
                q.eq("username", data);
                break;
            case 2:
                q.eq("mobile", data);
                break;
            case 3:
                q.eq("email", data);
                break;
            default:
                return false;
        }
        return count(q) == 0;
    }

    @Override
    public MemberEntity queryUser(String username, String password) {
        MemberEntity username1 = this.getOne(new QueryWrapper<MemberEntity>().eq("username", username));
        if (username1 == null) {
           throw new MemberException("用户名不存在");
        }
        if (!password.equals(username1.getPassword())) {
            throw new MemberException("密码错误");
        }

        return username1;
    }

}