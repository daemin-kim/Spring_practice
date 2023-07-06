package com.CNUSWAcademy.practice.frontControllerPattern.controllerV5;

import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.ControllerV3;
import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> params = createParamMap(request);
        
        return controller.process(params);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(it -> params.put(it, request.getParameter(it)));
        return params;
    }
}
