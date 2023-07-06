package com.CNUSWAcademy.practice.frontControllerPattern.controllerV5;

import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.ModelView;
import com.CNUSWAcademy.practice.frontControllerPattern.controllerV4.ControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> params = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(params, model);

        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return null;
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(it -> params.put(it, request.getParameter(it)));

        return params;
    }
}
