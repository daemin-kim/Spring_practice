package com.CNUSWAcademy.practice.frontControllerPattern.controllerV4;

import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private final Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---------- [FRONT CONTROLLER V4 실행] ----------");

        String requestURI = request.getRequestURI();

        ControllerV4 controller = getController(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> requestParamMap = createParamMap(request);

        HashMap<String, Object> model = new HashMap<>();

        String viewName = controller.process(requestParamMap, model);

        MyView view = viewResolver(viewName);

        view.render(model, request, response);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> requestParamMap = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(it -> requestParamMap.put(it, request.getParameter(it)));
        return requestParamMap;
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private ControllerV4 getController(String requestURI) {
        return controllerMap.get(requestURI);
    }
}