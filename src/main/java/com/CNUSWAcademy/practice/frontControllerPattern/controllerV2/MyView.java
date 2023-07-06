package com.CNUSWAcademy.practice.frontControllerPattern.controllerV2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    V1에서 발생한 코드 중복을 개선

    중복되는 코드

    String viewPath = "Something.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);

    해당 부분의 분리를 위해 View의 처리를 담당하는 객체를 만듬.

    최종 구조

    1. URL 매핑 정보에서 컨트롤러 조회
    2. 컨트롤러 호출
    3. MyView 반환
    4. render() 호출
    5. JSP 호출

    클라이언트가 프론트 컨트롤러에게 HTTP 요청 -> 프론트 컨트롤러는 URL 매핑 정보에서 컨트롤러 조회 -> 프론트 컨트롤러가 컨트롤러를 호출하고
    컨트롤러는 MyView를 반환 -> 프론트 컨트롤러에서 render() 호출 -> MyView에서 JSP forward -> HTML 응답

    이전에 중복되었던 코드들이 MyView에서 공통으로 처리됨.

 */

public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
