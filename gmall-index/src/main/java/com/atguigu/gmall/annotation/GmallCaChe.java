package com.atguigu.gmall.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GmallCaChe {
    String prefix() default "";

    /***
     * 以分为单位
     * 缓存key的前缀
     * @return
     */
    int timeout() default 5;

    int random() default  5;

}
