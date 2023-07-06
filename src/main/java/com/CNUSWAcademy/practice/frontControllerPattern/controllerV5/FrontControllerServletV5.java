package com.CNUSWAcademy.practice.frontControllerPattern.controllerV5;

import com.CNUSWAcademy.practice.frontControllerPattern.controllerV3.*;
import com.CNUSWAcademy.practice.frontControllerPattern.controllerV4.MemberFormControllerV4;
import com.CNUSWAcademy.practice.frontControllerPattern.controllerV4.MemberListControllerV4;
import com.CNUSWAcademy.practice.frontControllerPattern.controllerV4.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {

        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("---------- [FRONT CONTROLLER V5 실행] ----------");

        Object handler = getHandler(request);
        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getAdapterHandler(handler);

        ModelView modelView = adapter.handle(request, response, handler);
        MyView view = viewResolver(modelView.getViewName());
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappingMap.get(request.getRequestURI());
    }

    private MyHandlerAdapter getAdapterHandler(Object handler) {
        return handlerAdapters.stream()
                .filter(it -> it.supports(handler))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Handler Adapter를 찾을 수 없습니다. handler = %s". formatted(handler)));
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}