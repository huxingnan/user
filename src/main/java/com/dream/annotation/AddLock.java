package com.dream.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解为了加锁使用
 * Created by zhaosh on 2017/5/12.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AddLock {

    String spel() ;
    String logInfo() default "";
}
