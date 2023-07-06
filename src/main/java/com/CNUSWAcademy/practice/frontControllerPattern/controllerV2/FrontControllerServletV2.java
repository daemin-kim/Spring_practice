package com.CNUSWAcademy.practice.frontControllerPattern.controllerV2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---------- [FRONT CONTROLLER V2 실행] ----------");

        String requestURI = request.getRequestURI();  // 요청 URI 가져오기

        // 요청(Request)에 매핑(Mapping)되는 컨트롤러(Controller) 가져오기
        ControllerV2 controller = getController(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 NOT FOUND
            return;
        }

        // Controller 호출
        MyView view = controller.process(request, response);
        
        // V2 추가
        view.render(request, response);
    }

    private ControllerV2 getController(String requestURI) {
        return controllerMap.get(requestURI);
    }
}