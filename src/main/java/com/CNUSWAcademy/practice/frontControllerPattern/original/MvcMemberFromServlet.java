package com.CNUSWAcademy.practice.frontControllerPattern.original;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    프론트 컨트롤러 도입 전에는 각각의 컨트롤러마다 공통되는 기능과 추가적인 기능을 모두 작성해야 했음.

    HttpServletRequest, HttpResponse response 각각은 사용하지 않는 경우도 존재하며, 심지어 Response 는 한번도 사용하지 않았음.

    이러한 이유 때문에, 기능이 복잡해지고 프로젝트의 규모가 커질수록 공통으로 처리해주어야 하는 부분이 계속해서 증가될 것이며,
    공통되는 기능을 처리하기 어려워질 것임.

    이는 프론트 컨트롤러 패턴을 도입함으로써 해결할 수 있음.

    프론트 컨트롤러 패턴이란?

    -> 컨트롤러 호출 전에 공통되는 기능을 처리하기 위한 컨트롤러를 사용하는 패턴을 의미함.

    이때 사용되는 컨트롤러는 모든 요청이 처음으로 들어오는 입구의 역할을 하게되며, 이를 프론트 컨트롤러라 부름.

    프론트 컨트롤러 도입 이후에는 프론트 컨트롤러에서 공통의 기능을 수행한 뒤, 각각의 요청에 대응되는 컨트롤러를 호출함으로써
    공통 기능의 처리가 가능해짐.

    프론트 컨트롤러 패턴의 특징

    1. 프론트 컨트롤러 서블릿 하나를 통해 클라이언트의 모든 요청을 받음.
    2. 프론트 컨트롤러는 요청(Request)에 매핑(mapping)되는 컨트롤러를 찾아서 호출함
    3. 모든 요청의 입구가 프론트 컨트롤러가 됨.
    4. 프론트 컨트롤러에서는 공통의 로직을 처리할 수 있음.
    5. 또한 프론트 컨트롤러를 제외하면 나머지 컨트롤러는 서블릿을 사용할 필요가 없어짐.
       (프론트 컨트롤러에서 단순 메서드를 호출하는 것을 통해 다른 컨트롤러를 호출할 수 있기 때문에)

    스프링 웹 MVC도 Front Controller 패턴을 사용하며, 이때 DispatcherServlet이 Front Controller의 역할을 수행함.


 */


@WebServlet(name = "mvcMemberFromServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFromServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);

        dispatcher.forward(request, response);
    }
}
