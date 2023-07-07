package com.CNUSWAcademy.practice.SpringInterceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public interface HandlerInterceptor {
    
    // 컨트롤러 호출 전에 호출
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    
    // 컨트롤러 호출 후에 호출
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable ModelAndView modelAndView) throws Exception {}
    
    // 요청 완료 이후
    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {}

    /*
      인터셉터의 경우 어떤 컨트롤러(handler)를 호출하는지에 대한 정보도 받을 수 있다.

      인터셉터 예외 발생 시

        컨트롤러에서 예외가 발생한다면 컨트롤러 호출 후에 호출되는 postHandle은 호출되지 않는다.
        afterCompletion은 예외가 발생해도 호출되는데, 예외가 발생한 경우라면 어떤 예외가 발생했는지 파라미터로 받아서
        로그로 출력할 수도 있다.

      인터셉터 등록 메서드

      WebMvcConfigurer을 구현한 후, addInterceptors를 Override하여 등록해주면 된다.

      @Configuration
      public String WebConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**","/*.ico");
        }
     }

     */
}
