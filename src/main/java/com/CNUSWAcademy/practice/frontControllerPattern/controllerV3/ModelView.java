package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import java.util.HashMap;
import java.util.Map;

/*
    V3에서는 컨트롤러에서 서블릿으로의 총속성을 제거하면서, 뷰 이름의 중복도 제거하도록 함

    Servlet 종속성 제거

    컨트롤러에서 HttpServletRequest와 HttpServletResponse를 사용하는 대신,
    요청 파라미터 정보를 Map을 통해 넘기도록 하면, HttpServletRequest에 대한 의존성을 없앨 수 있음.
    또한 Request에서 serAttribute를 통해 데이터를 보관하는 대신, 별도의 Model 객체를 만들어 반환함으로써, 의존성을 없앨 수 있음

    또한 현재 컨트롤러에서는 HttpServletResponse를 사용하지 않기 떄문에 이 역시 의존성을 없앨 수 있음.

    V3에서는 컨트롤러는 뷰의 논리적인 이름만 반환하며, 실제 물리적인 위치는 프론트 컨트롤러에서 처리하도록 변경함.

    최종 구조

    1. 컨트롤러 조회
    2. 컨트롤러 호출
    3. ModelView 반환
    4. viewResolver 호출
    5. MyView 반환
    6. render(model) 호출

    클라이언트가 HTTP 요청을 프론트 컨트롤러에게 보냄 -> 프론트 컨트롤러는 매핑 정보를 통해 컨트롤러를 조회함 -> 프론트 컨트롤러에서 컨트롤러를 호출
    -> 컨트롤러는 ModelView를 반환 -> 프론트 컨트롤러에서 viewResolver를 호출 -> viewResolver는 MyView를 반환 -> 프론트 컨트롤러에서 render(model)을 호출
    -> Myview에서 HTML 응답

 */
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setAttribute(String key, Object value) {
        model.put(key, value);
    }

    public void setModel(Map<String, Object> model) {
    }
}
