package com.atguigu.gmall.search.respository;

import com.atguigu.gmall.search.pojo.Goods;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodRespository extends ElasticsearchRepository<Goods,Long> {
}
