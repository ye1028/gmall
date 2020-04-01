package com.atguigu.gmall.search.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("wms-server")
public interface GmallWmsApi extends com.atguigu.gmall.wms.api.GmallWmsApi {
}
