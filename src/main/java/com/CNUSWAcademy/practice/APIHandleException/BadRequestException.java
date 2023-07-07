package com.CNUSWAcademy.practice.APIHandleException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
  ResponseStatusExceptionResolver

  @ResponseStatus가 달려있는 예외
  ResponseStatusException 예외

  DefaultHandlerExceptionResolver

  DefaultHandlerExceptionResolver는 스프링 내부에서 발생하는 스프링 예외를 해결한다.
  예를 들어 파라미터 바인딩 시점에 타입이 맞지 않으면 내부에서 TypeMismatchException이 발생하는데, 이 경우에는
  예외가 발생한 것이기 때문에 그냥 두면 서블릿 컨테이너까지 오류가 전달되며, 결과적으로 500오류가 발생한다.

  그러나 파라미터 바인딩은 대부분 클라이언트의 실수이므로 DefaultHandlerExceptionResolver에서는 이것을 500 오류가 아니라 HTTP 상태코드 400 오류로 변경한다.
 */

// @ResponseStatus가 있는 예외
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
public class BadRequestException extends RuntimeException {}

// ResponseStatusException 예외
// throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error.bad", new illigalArgumentException);
