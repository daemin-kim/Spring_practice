package com.CNUSWAcademy.practice.ServletHandleException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
  서블릿은 Exception 혹은 response.sendError()가 호출되었을 때, WAS까지 전파된다면, WAS는 해당 예외를 처리하는 오류 페이지 정보를 찾는다.
  RuntimeException은 오류 페이지로 /error/500이 지정되어 있으며, WAS는 오류 페이지를 출력하기 위해 /error-page/500을 "다시 요청"한다

  "다시 요청"한다는 것이 중요하다.
  전체적인 예외 발생시 요청 흐름

  1. WAS -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러 // 요청이 들어온 경우
  2. 컨트롤러(예외 발생) -> 인터셉터 -> 서블릿 -> 필터 -> WAS(여기까지 전파) // 컨트롤러에서 예외가 발생한 경우
  3. WAS "/error-page/500" -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러("/error-page/500") 처리

  DispatcherType

  오류가 발생하면 오류 페이지를 출력하기 위해 WAS 내부에서 다시 한번 호출이 발생한다.
  이때 필터와 인터셉터도 모두 다시 호출되는데, 에러 페이지를 띄우기 위해 내부에서 일어니는 호출에 대해 필터와 인터셉터를 작동시키지 않은 경우에는 어떻게 해야 할까
  이를 방지하기 위해서는 클라이언트로부터 발생한 정상 요청인지, 아니면 오류 페이지를 출력하기 위한 내부 요청인지 이 둘을 구분할 수 있어야 한다.

  서블릿에서는 이러한 문제를 해결하기 위해 DispatcherType이라는 추가 정보를 제공한다.

  고객이 처음 요청했을 경우 DispatcherType은 REQUEST이다. 그리고 오류 페이지를 불러오기 위한 요청에서 DispatcherType은 ERROR이다.
  이렇듯 DispatcherType을 통해 요청을 구분할 수 있으며, 이 외에도 여러 DispatcherType이 존재한다.

  REQUEST : 클라이언트 요청
  ERROR : 오류 요청
  FORWARD : 서블릿에서 다른 서블릿이나 JSP를 호출할 때
  INCLUDE : 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때
  ASYNC : 서블릿 비동기 호출

  그리고 필터에 대해 DispatcherType을 적용하고 싶은 경우, FilterRegistrationBean.setDispatcherTypes를 지정해주면 된다.
  기본값은 DispatcherType.REQUEST임으로, 클라이언트의 요청이 있는 경우에만 필터가 적용된다.

  인터셉터의 경우 excludePathPatterns를 사용하며, 오류 페이지 경로에 대해 인터셉터가 호출되지 않도록 하는 방법이 있다.
 */

@Slf4j
@Controller
public class ErrorPageController {

    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.MESSAGE";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        prinfErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        prinfErrorInfo(request);
        return "error-page/500";
    }

    private void prinfErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex={}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MASSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatcherType= {}", request.getDispatcherType());
    }
}
