package com.CNUSWAcademy.practice.ServletFilter;

import jakarta.servlet.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public interface Filter {

    // 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때, 호출된다.
    public default void init(FilterConfig filterConfig) throws ServletException {}
    // 고객의 요청이 들어올 때마다 해당 메서드가 호출된다. 필터의 로직을 작성한다.
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException;
    // 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출된다.
    public default void destory() {}

    // 참고 사항 -> 필터를 등록하면, 서블릿 컨테이너가 필터를 싱글톤 객체로 생성하고 관리해주기 때문에 사용에 주의해야 한다.

    /*
      서블릿 필터

        필터를 사용하는 이유는 로그인 처리 등과 같은, 애플리케이션에서 공통적으로 관심이 있는 로직을 처리하기 위함.
        스프링 AOP로도 처리할 수 있지만, 웹과 관련된 공통 관심사는 필터 도는 인터셉터를 사용하는 것이 좋다.
        웹과 관련된 로직을 처리할 때에는 HTTP의 정보가 필요한데, 필터나 인터셉터는 HttpServletRequest, HttpServletResponse를 제공하기 때문이다.

      필터의 흐름

        Http 요청 -> WAS -> 필터 -> (디스패처)서블릿 -> 컨트롤러
        필터를 호출한 다음에 서블릿이 호출되기 때문에, 요청 로그를 남기는 등의 로직을 처리하기 위해서는 필터를 사용하면 좋다.

      필터의 제한

        Http 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러 // 정상 흐름
        Http 요청 -> WAS -> 필터(종료) // 적절하지 않은 요청이라 판단될 경우, 서블릿 호출 X

      필터 체인

        Http 요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> 서블릿 -> 컨트롤러
        필터는 체인으로 구성되어, 중간에 필터를 자유롭게 추가하거나 제거할 수 있다.


      필터 등록 메서드

      @Bean
      public FilterRegistrationBean filter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new 등록할필터);
        filterRegistrationBean.setOrder(순서, 번호가 낮을수록 먼저 실행);
        filterRegistrationBean.addUrlPatterns("적용할  URL");

        return filterRegistrationBean;
      }

     */
}
