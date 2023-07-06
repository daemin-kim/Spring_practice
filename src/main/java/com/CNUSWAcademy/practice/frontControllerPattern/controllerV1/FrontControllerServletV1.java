package com.CNUSWAcademy.practice.frontControllerPattern.controllerV1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    V1 : 요청에 매핑되는 컨트롤러를 찾아서 호출하는 프론트 컨트롤러

    개선 사항 -> 모든 요청에 대해 프론트 컨트롤러 서블릿이 해당 요청에 매핑되는 컨트롤러를 찾아서 호출하게 됨
                프론트 컨트롤러 이외의 다른 컨트롤러에서는 서블릿을 사용하지 않게됨

    최종 구조
    1. URL 매핑 정보에서 컨트롤러 조회
    2. 컨트롤러 호출
    3. 컨트롤러에서 JSP forward

    클라이언트가 Front Controller에게 HTTP 요청을 보냄 -> Front Controller가 URL 매핑 정보에서 Controller 조회
    -> Controller 호출 -> Controller에서 JSP forward -> HTML 응답
 */

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFromControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---------- [FRONT CONTROLLER V1 실행] ----------");

        String requestURI = request.getRequestURI();

        ControllerV1 controller = getController(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    private ControllerV1 getController(String requestURI) {
        return controllerMap.get(requestURI);
    }

}
