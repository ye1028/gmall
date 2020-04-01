package com.atguigu.gmall.auth.service;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.auth.Properties.JwtProperties;
import com.atguigu.gmall.auth.feign.AuthFeign;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.gmall.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {


    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthFeign authFeign;

    public String accredit(String username, String password) throws Exception {
        Resp<MemberEntity> memberEntityResp = authFeign.queryUser(username, password);
        MemberEntity data = memberEntityResp.getData();
        if (data != null) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",data.getId());
            map.put("username",data.getUsername());
            String token = JwtUtils.generateToken(map, this.jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        }
        return null;

    }
}
