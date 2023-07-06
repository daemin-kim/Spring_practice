package com.CNUSWAcademy.practice.frontControllerPattern.controllerV1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    process() 메서드 내부에 있는 로직은 바뀌지 않았지만, HttpServlet을 상속받지 않고 구현하였기 때문에 더 이상 Servlet이 아님
 */

public class MemberFromControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);

        dispatcher.forward(request, response);
    }
}
