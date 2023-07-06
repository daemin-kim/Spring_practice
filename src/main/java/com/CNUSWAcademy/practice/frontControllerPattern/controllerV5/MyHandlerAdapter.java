package com.CNUSWAcademy.practice.frontControllerPattern.controllerV5;

import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


/*
    기호, 상황에 따라 ControllerV3, ControllerV4를 사용하기 위해, 어떠한 컨트롤러 인터페이스를 구현하든지 상관없이
    모두 지원하기 위하여 어댑터 패턴을 도입하도록함.

    프론트 컨트롤러에서 지원되지 않는 컨트롤러 양식을 지원하기 위해, 중간에 어댑터를 추가함.

    어댑터는 프론트 컨트롤러와 프론트 컨트롤러에서 지원되지 않는 타입의 컨트롤러 사이에서 이들을 맞춰주는 역할을 수행함.

    V5에서는 어댑터를 통해 컨트롤러가 아니더라도 해당하는 종류의 어댑터만 있으면, 이들을 다 처리(handle)할 수 있기에, 컨트롤러
    대신에 Handler라는 용어를 사용함

    최종 구조
    1. 핸들러 조회
    2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
    3. handle(handler)
    4. handler 호출
    5. ModelView 반환.
    6. viewResolver 호출
    7. MyView 반환.
    8. render(model) 호출

    클라이언트가 HTTP 요청을 프론트 컨트롤러에게 보냄 -> 프론트 컨트롤러는 핸들러 매핑 정보를 통해 핸들러를 조회
    -> 핸들러 어댑터 목록을 보고 핸들러를 처리할 수 있는 핸들러 어댑터를 조회 -> 프론트 컨트롤러는 handle(handler)를 통해 핸들러 어댑터에 접근
    -> 핸들러 어댑터는 handler를 호출하여 핸들러(컨트롤러)에게 접근 -> 핸들러 어댑터는 ModelView를 반환함.
    -> 프론트 컨트롤러는 viewResolver를 호출하며, viewResolver는 MyView를 반환함.
    -> 프론트 컨트롤러는 render(model)을 호출하고, MyView에서는 HTML을 응답함.

    핸들러 매핑 정보는 요청에 매핑되는 핸들러에 대한 정보를 의미함.
    핸들러 어댑터(= 컨트롤러)는 핸들러를 처리(내부에서 핸들러의 메서드를 호출)함.

 */

public interface MyHandlerAdapter {
    boolean supports(Object handler);
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
