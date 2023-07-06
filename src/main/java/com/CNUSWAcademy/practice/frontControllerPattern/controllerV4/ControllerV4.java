package com.CNUSWAcademy.practice.frontControllerPattern.controllerV4;

import java.util.Map;

/*
    V3에서 대부분의 기능 개선이 이루어졌으나, 항상 ModelView 객체를 생성하고 반환하는 코드를 컨트롤러마다 작성해 주어야 했음.
    V4에서는 중복 작업을 줄여보고자 하였음.

    V4에서는 ModelView대신 View의 이름만 반환하도록 개선함.

    최종 구조
    1. 컨트롤러 조회
    2. 호출(paramMap, model)
    3. ViewName 반환
    4. viewResolver 호출
    5. MyView 반환
    6. render(model) 호출

    클라이언트가 프론트 컨트롤러에게 HTTP 요청을 보냄 -> 프론트 컨트롤러는 매핑 정보에서 알맞은 컨트롤러를 조회 -> 프론트 컨트롤러는 paramMap, model 정보를 이용해 컨트롤러를 호출
    -> 컨트롤러는 viewName을 반환함 -> 프론트 컨트롤러는 viewResolver를 호출함 -> viewResolver는 MyView를 반환. -> 프론트 컨트롤러는 render(model)을 호출 -> MyView에서 HTML 응답.

 */

public interface ControllerV4 {

    String process(Map<String, String> request, Map<String, Object> model);
}
