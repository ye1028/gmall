package com.atguigu.gmall.sms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class YxVo {
    private Long skuId;

    // 积分相关
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    private List<Integer> work;
    private Integer fullCount;

    // 折扣
    private BigDecimal discount;
    private BigDecimal price;
    private Integer ladderAddOther;

    //满减
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;
}
