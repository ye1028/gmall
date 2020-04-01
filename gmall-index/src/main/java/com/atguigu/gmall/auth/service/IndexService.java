package com.atguigu.gmall.auth.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.annotation.GmallCaChe;
import com.atguigu.gmall.auth.feign.GmallPmsClient;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.vo.CategoryVo;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {

    private static final String pre = "index:category";


    @Autowired
    private RedissonClient client;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GmallPmsClient gmallPmsClient;

    public List<CategoryEntity> queryCategory() {
        Resp<List<CategoryEntity>> listResp = gmallPmsClient.queryCate(1, null);
        return listResp.getData();
    }

    //根据一级查二三级
    @GmallCaChe(prefix = "index:cates",timeout = 7200,random = 100)
    public List<CategoryVo> quertCategory2(Long pid) {
        //1.判断缓存中是否有
        String cateJson = redisTemplate.opsForValue().get(pre + pid);

        //缓存中有
        if (!StringUtils.isEmpty(cateJson)) {
            List<CategoryVo> categoryVos = JSON.parseArray(cateJson, CategoryVo.class);
            return categoryVos;
        }

        //分布式锁
        RLock lock = client.getLock("lock"+pid);
        lock.lock();
        if (!StringUtils.isEmpty(cateJson)) {
            lock.unlock();
            List<CategoryVo> categoryVos = JSON.parseArray(cateJson, CategoryVo.class);
            return categoryVos;
        }

        Resp<List<CategoryVo>> listResp = gmallPmsClient.querySubCate(pid);
        redisTemplate.opsForValue().set(pre + pid, JSON.toJSONString(listResp.getData()));
        lock.unlock();
        return listResp.getData();
    }

}
