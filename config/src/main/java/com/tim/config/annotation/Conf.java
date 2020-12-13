package com.tim.config.annotation;

import java.lang.annotation.*;

/**
 * @description: 配置属性注解
 * @author: li si
 * @create: 2020-09-19 14:54
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conf {
    String name();
}
