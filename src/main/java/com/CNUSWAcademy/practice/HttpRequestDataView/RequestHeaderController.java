package com.CNUSWAcademy.practice.HttpRequestDataView;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/*
    HttpMethod -> HTTP 메소드를 조회함.
    Locale -> Locale 정보를 조회함.
    @RequestHeader MultiValueMap<String, String> headerMap -> 모든 HTTP 헤더를 MultiValueMap 형식으로 조회함.
    @RequestHeader("host") String host -> 특정 HTTP를 조회함.
    @CookieValue(value = "myCookie", required = false) String cookie -> 특정 쿠키를 조회함

    HTTP 요청 데이터를 전달하는 방법 3가지

    GET : 메시지 바디 없이 URL의 쿼리 파라미터 데이터를 포함하여 전달하는 방식, 주로 검색 시 필터링, 페이징을 하기 위해 많이 사용
    ex) /url?username=hello&age=20

    POST : 회원 가입 등에 사용하는 방식, HTML Form
    ex) content-type : application/x-www-form-urlencoded

    GET의 쿼리 파라미터 전송 방식, POST의 HTML Form 전송 방식 모두 형식이 같기 때문에, 이 둘을 구분없이 조회할 수 있음
    이것을 간단히 요청 파라미터(Request Parameter)조회라 부름

    HTTP message body에 직접 데이터를 담아서 요청 -> 대표적인 예시로 JSON, HTTP API에서 주로 사용함

 */
@Slf4j
@RestController

public class RequestHeaderController {
    ObjectMapper objectMapper = new ObjectMapper();
    @RequestMapping("/headers")
    public String headers(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod httpMethod,
            Locale locale,
            @RequestHeader MultiValueMap<String, String> headerMap,
            @RequestHeader("host") String host,
            @CookieValue(value = "myCookie", required = false) String cookie
    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
    /*
        요청 파라미터를 조회하는 방법

        요청 -> ~~/path?username=kim&age=23
     */

    // HttpServletRequest 사용
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);
    }

    // RequestParam 사용
    @RequestMapping("/request-param-v2")
    public void requestParamV2(@RequestParam("username") String memberName,
                               @RequestParam("age") int memberAge
    ) {
      log.info("username={}, age={}", memberName, memberAge);
    }

    // HTTP 파라미터 이름이 변수 이름과 같으면 name="xx"에 대한 생략이 가능함
    @RequestMapping("/request-param-v3")
    public void requestParamV3(@RequestParam String username,
                               @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
    }

    // HTTP 파라미터 이름이 변수 이름과 같을 때, String, int, Integer 등의 단순 타입이면, @RequestParam도 생략이 가능함.
    @RequestMapping("/request-param-v4")
    public void requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
    }

    /*
       RequestParam 사용 시, 반드시 있어야 하는 값이 있다면, required를 통해 이를 설정할 수 있음
       RequestParam 어노테이션을 생략하면, 스프링 MVC는 내부에서 required=false를 적용함

       기본값은 true임.
       만약 필수 파라미터가 없을 경우, 400 예외가 발생하고, username= 처럼 뒤에 아무것도 입력하지 않는 경우 빈 문자("")로 통과됨

       @RequestParam(required = false) int age 처럼 primitive type의 required가 false인 경우, 아무런 값을 입력하지 않으면 null로 들어오므로 예외가 발생함.
       null이 들어올 가능성이 있는 타입은 Integer처럼 래퍼 클래스로 감싸거나, 혹은 defaultValue를 사용해야함.
     */

    @RequestMapping("/request-param-required")
    public void requestParamRequired(@RequestParam(required = true) String username,
                                     @RequestParam(required = false) Integer age)
    {
        log.info("username={}, age={}", username, age);
    }

    /*
       defaultValue 사용
       defalutValue는 빈 문자의 경우에도 적용됨.
       defaultValue를 설정하였다면, required 속성이 필요없다.(없으면 기본값이 세팅됨.)

       username= 과 같이 빈 문자가 들어오는 경우에도 빈 문자열("")이 아닌 기본 문자열이 적용됨.
       또한 defaultValue를 사용한 경우 요청 파라미터 값이 없는 경우 자동으로 defaultValue로 지정한 값이 들어가기 때문에
       required = true를 해주지 않아도 됨.
     */

    @RequestMapping("/request-param-default")
    public void requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {
        log.info("username={}, age={}", username, age);
    }

    /*
       파라미터를 Map, MultiValueMap으로 조회하기
       Map -> 하나의 key에 value가 한 개인 경우 사용함
       MultiValueMap -> 하나의 key에 value가 여러 개인 경우 사용함
       
       파라미터 값이 1개인 것이 확실하다면 Map을 사용하지만, 그렇지 않다면 MultiValueMap을 사용하여야 함.
       (Map을 사용하였는데 여러개를 입력한다면 맨 처음 입력한 것이 출력됨)
     */

    @RequestMapping("/request-param-map")
    public void requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
    }

    /*
      @RequestParam을 통해 클라이언트에서 보낸 파라미터를 받아올 수 있지만, 대부분의 경우 받아온 파라미터를 사용해서 객체를 만들어 사용함
      이러한 과정을 간편하게 진행하기 위해 스프링이 제공하는 @ModelAttribute를 사용할 수 있다
      이를 통해 파라미터를 바로 객체 형태로 만들어서 받을 수 있음
    */

    // 기존 @RequestParam만을 사용하는 경우
    @RequestMapping("/model-attribute-v1")
    public void modelAttributeV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    // @ModelAttribute를 사용한 경우
    @RequestMapping("model-attribute-v1")
    public void modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    /*
      @ModelAttribute는 생략 가능함.
      @ModelAttribute와 @RequestParam은 모두 생략이 가능한데, 적용되는 규칙은 다음과 같다.
      String, int, Integer와 같은 단순 타입 -> @RequestMapping
      (Argument Resolver로 지정해준 타입 외) 나머지 객체 -> @ModelAttribute
      하지만 혼동되지 않도록 어노테이션을 사용하는 것을 권장함.
      
      ModelAttribute가 값을 바인딩하기 위한 조건
      
      기본 생성자와 Setter가 존재한다 or 모든 필드를 매개변수로 받는 생성자가 필요하다 or 몇개의 필드를 매개변수로 받는 생성자 + setter
     */
    
    @RequestMapping("/model-attribute-v2")
    public void modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    /*
      요청 메시지를 조회하는 방법 -> 단순 텍스트
      HTTP message body에 데이터를 직접 담아 요청하는 경우, 위에서 살펴본 요청 파라미터를 조회하는 방법으로는
      이에 대한 정보를 가져올 수 없음.

     */

    // 가장 기본적인 방법
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
    }

    // InputStream을 바로 파라미터로 받는 방법
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
    }

    // HttpEntity를 사용하는 방법
    @PostMapping("/request-body-string-v3")
    public void requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
    }

    // @RequestBody를 사용하는 방법
    @PostMapping("/request-body-string-v4")
    public void requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
    }

    // 위의 모든 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam, @ModelAttribute 와는
    // 관계가 없음, 메시지 바디를 처리하는 기능은 HTTP 메시지 컨버터에서 수행됨.

    /*
      요청 메시지를 조회하는 방법 -> JSON
      JSON을 조회하는 방법도 단순 텍스트를 조회하는 방법과 비슷하지만, ObjectMapper를 사용할 수 있다.
     */

    @PostMapping("/request-body-json-v1")
    public void requestBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    // @RequestBody + ObjectMapper의 조합도 가능함.
    @PostMapping("/request-body-json-v2")
    public void requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    /*
      @RequestBody 사용 -> 가장 추천되는 방법으로 @RequestBody를 통해 객체를 지정하는 것임
      @RequestBody에는 직접 만든 객체를 저장할 수 있다.
      HTTPEntity, @RequestBody를 사용하게 되면, HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 파라미터로 지정된 문자나
      객체 등으로 변환해주는 작업을 수행한다.
      이는 JSON도 객체로 변환해준다
     */
    @PostMapping("/request-body-json-v3")
    public void requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    }

    /*
      주의사항

      1. @RequestBody는 생략이 불가능함. 스프링은 @ModelAttribute, @RequestParam 해당 생략시, 다음과 같은 규칙을 적용한다.
      String, int, Integer 같은 단순 타입 = @RequestParam
      나머지 -> @ModelAttribute (argument resolver로 지정해둔 타입 외)
      따라서 @RequestBody를 생략하면 @ModelAttribute가 적용되어버린다.

      2. HTTP 요청 시 content-type이 반드시 application/json 이어야 한다. -> 그래야만 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
     */
}