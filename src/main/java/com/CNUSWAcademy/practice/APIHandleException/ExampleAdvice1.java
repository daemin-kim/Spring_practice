package com.CNUSWAcademy.practice.APIHandleException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.AbstractController;

/*
  @ControllerAdvice

  @ControllerAdvice는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여해주는 역할을 한다.
  @ControllerAdvice에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다.(글로벌 적용)

 */

@ControllerAdvice(annotations = RestController.class)
public class ExampleAdvice1 {}

@ControllerAdvice("org.example.controllers")
class ExampleAdvice2 {}

@ControllerAdvice(assignableTypes = {/*ControllerInterface.class, */ AbstractController.class})
class ExampleAdvice3 {}
