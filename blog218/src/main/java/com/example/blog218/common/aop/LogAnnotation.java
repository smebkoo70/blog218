package com.example.blog218.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 * type 代表可以放在类上面 method 代表可以放在方法上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
