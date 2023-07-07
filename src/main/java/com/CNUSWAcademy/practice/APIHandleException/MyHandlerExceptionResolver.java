package com.CNUSWAcademy.practice.APIHandleException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/*
  HandlerExceptionResolver

  예외가 발생해서 서블릿을 넘어 WAS까지 전달된다면, 이전에 알아봤던 것과 같이 HTTP 상태코드 500으로 처리된다
  발생하는 예외에 따라서 상태코드를 변경하고 싶은 경우에 스프링은 컨트롤러 밖으로 예외가 던져진 경우 해당 예외를 해결하고 동작을 새롭게 정의할 수 있는 방법을 제공한다.
  이런 경우에 사용할 수 있는 것이 바로 HandlerExceptionResolver이다. 줄여서 ExceptionResolver라고 한다.

  ExceptionResolver가 적용되기 전에는, 컨트롤러에서 예외가 발생하면, WAS까지 예외가 전달되어 500을 반환한다.
  그러나 ExceptionResolver가 적용된다면, 컨트롤러에서 발생한 예외를 ExceptionResolver가 처리한 후, WAS에는 정상 응답으로 넘어가게 된다.

  스프링 부트는 예외처리를 좀 더 편하게 할 수 있도록 기본으로 몇가지 ExceptionResolver를 제공한다

  1. ExceptionHandlerExceptionResolver
  2. ResponseStatusExceptionResolver
  3. DefaultHandlerExceptionResolver -> 우선순위가 가장 낮음.

  ExceptionHandlerExceptionResolver

  @ExceptionHandler를 처리함. API 예외 처리를 대부분 이것으로 해결함

  ResponseStatusExceptionResolver

  HTTP 상태 코드를 지정해준다.

  ex) @ResponseStatus(value = HttpStatus.NOT_FOUND)

  DefaultHandlerExceptionResolver

  스프링 내부 기본 예외를 처리함.

 */

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    // illegalArgumentException이 발생한다면, 이를 HTTP 상태코드 400으로 처리하는 예시
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("illegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 빈 ModelAndView를 반환한 이유 -> 빈 ModelAndView를 반환하면 뷰를 렌더링 하지 않으면서 정상 흐름으로 서블릿이 리턴된다.
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;
    }
}
