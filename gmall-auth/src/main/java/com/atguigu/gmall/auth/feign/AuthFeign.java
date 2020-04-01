package com.atguigu.gmall.auth.feign;

import com.atguigu.gmall.ums.api.umsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("auth-server")
public interface AuthFeign  extends umsApi {
}
