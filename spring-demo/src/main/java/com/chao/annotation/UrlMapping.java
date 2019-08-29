package com.chao.annotation;

import java.lang.annotation.*;

/**
 * @Filename: Controller
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/27 ;
 */

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UrlMapping {
    String value() default "";
}
