package com.atguigu.gmall.auth.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.auth.Properties.JwtProperties;
import com.atguigu.gmall.auth.service.AuthService;
import com.atguigu.gmall.ums.entity.MemberEntity;
import com.atguigu.gmall.auth.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("accredit")
    public Resp<Object> accedit(MemberEntity m, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = m.getUsername();
        String password = m.getPassword();
        String accredit = authService.accredit(username, password);
        if (!StringUtils.isEmpty(accredit)) {
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(),accredit,jwtProperties.getExpire());
        }
        return Resp.ok(null);
    }

}
