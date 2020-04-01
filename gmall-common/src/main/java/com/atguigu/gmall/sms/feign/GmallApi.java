package com.atguigu.gmall.sms.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.vo.YxVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GmallApi {
    @PostMapping("/sms/skubounds/sku/save")
    public Resp<Object> saveSale(@RequestBody YxVo yxVo);
}
