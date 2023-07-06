package com.CNUSWAcademy.practice.frontControllerPattern.controllerV1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    프론트 컨트롤러 이외의 다른 컨트롤러에서는 서블릿을 사용하지 않기 위해서, 서블릿과 비슷한 생김새의 Controller 인터페이스를 선언

    프론트 컨트롤러를 제외한 각 컨트롤러는 해당 인터페이스를 구현함
    프론트 컨트롤러에서는 해당 인터페이스를 구현한 컨트롤러들 중 요청에 매핑되는 컨트롤러를 찾아 호출하는 일관적인 로직을 통해
    여러 요청을 처리할 수 있음.
 */

public interface ControllerV1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
