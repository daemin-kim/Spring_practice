package com.CNUSWAcademy.practice.HttpMessageConverter;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.List;

/*
  스프링에선 HandlerMethodArgumentResolver와 HandlerMethodReturnValueHandler, HttpMessageConverter를 모두 인터페이스로
  제공하므로, 필요에 따라서 기능을 확장하여 사용할 수 있음.
  
  기능을 확장하기 위해선 WebConfigurer를 상속 받아서 스프링 빈으로 등록하면 됨.
  (addArgumentResolvers, extendMessageConverters 등 사용 가능)
 */

public interface WebMvcConfigurer {


    default void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {}


    default void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {}


    default void extendMessageConverters(List<HttpMessageConverter<?>> converters) {}

}