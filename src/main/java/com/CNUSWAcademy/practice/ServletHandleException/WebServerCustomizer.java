package com.CNUSWAcademy.practice.ServletHandleException;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    /*
      에러 페이지 커스텀하기
      Tomcat이나 스프링이 기본적으로 제공하는 에러 페이지 대신, 조금 더 예쁘게 에러 페이지를 꾸미고 싶은 경우, 커스텀 에러 페이지를 등록하는 것이 가능하다.
      WebServerFactoryCustomer<ConfigurableWebServerFactory>를 상속받아 customize 메서드를 오버라이딩하여 구현할 수 있으며, 해당 요청을 처리할 컨트롤러를 등록해야 한다.
     */
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }

    /*
      1. Exception의 처리 방식

      Java의 메인 메서드를 직접 실행하는 경우, main이라는 이름의 쓰레드가 실행된다.
      만약 실행 도중에 예외를 잡지 못하고 처음 실행한 main() 메서드를 넘어서 예외가 던져지면, 예외 정보를 남기고 해당 쓰레드는 종료된다.

      웹 애플리케이션의 경우 사용자 요청별로 별도의 쓰레드가 할당되고, 이는 서블릿 컨테이너 안에서 실행된다.
      애플리케이션에서 예외가 발생한 경우, try-catch로 잡지 못하고 서블릿 컨테이너 밖으로 예외가 전달되면 어떻게 될까?

      컨트롤러(예외 발생) -> 인터셉터 -> 서블릿 -> 필터 -> WAS(여기까지 전파됨)

      해당 경우에는, Tomcat이 기본으로 제공하는 HTTP 상태코드 500번의 오류 화면을 띄우게 됨.
      (스프링 부트 사용의 경우 스프링 부트가 제공하는 오류 페이지를 띄움)

      Exception의 경우, 서버 내부에서 처리할 수 없는 오류가 발생한 것으로 처리하여 HTTP 500을 반환하는 것임.

      2. SendError

      컨트롤러(response.sendError()) -> 인터셉터 -> 서블릿 -> 필터 -> WAS(sendError 호출 기록 확인)

      response.sendError()를 호출하면, response 내부에는 오류가 발생했다는 상태를 저장하게 된다.
      그리고 서블릿 컨테이너는 client에게 응답하기 전에 response에 sendError() 호출되었는지 확인한다.
      그리고 만약 호출되었다면, 설정한 오류 코드에 맞추어 Tomcat의 기본 오류 페이지를 보여준다.

     */
}
