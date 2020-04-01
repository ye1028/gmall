package com.atguigu.gmall.pms.controller.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.feign.GmallApi;
import com.atguigu.gmall.sms.vo.YxVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("sms-server")
public interface GmallSmsClient extends GmallApi {
}
