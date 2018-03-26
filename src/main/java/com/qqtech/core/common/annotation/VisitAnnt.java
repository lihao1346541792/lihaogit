package com.qqtech.core.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qqtech.core.common.enums.RespEnum;

/**
 * 访问限制注解类(默认是限制需要登陆)
 * 
 * @author andy.wangzhh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VisitAnnt {
	RespEnum type() default RespEnum.OK;
}