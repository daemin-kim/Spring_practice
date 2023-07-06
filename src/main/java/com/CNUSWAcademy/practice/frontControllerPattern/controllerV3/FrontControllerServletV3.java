package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---------- [FRONT CONTROLLER V3 실행] ----------");

        String requestURI = request.getRequestURI();

        // 요청에 매핑되는 컨트롤러 가져오기
        ControllerV3 controller = getController(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // (1) HttpServletRequest -> Map으로 변환
        HashMap<String, String> requestParamMap = createParamMap(request);

        // Controller를 호출
        ModelView modelView = controller.process(requestParamMap);

        // (2) View 생성
        MyView view = viewResolver(modelView.getViewName());
        
        // (3) Model 추가
        view.render(modelView.getModel(), request, response);

    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB_INF/views/" + viewName + ".jsp");
    }

    private static HashMap<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> requestParamMap = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(it -> requestParamMap.put(it, request.getParameter(it)));
        return requestParamMap;
    }

    private ControllerV3 getController(String requestURI) {
        return controllerMap.get(requestURI);
    }
}
