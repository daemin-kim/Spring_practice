package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    // view의 이름이 /WEB-INF/views/new-form.jsp에서 new-form으로 변경됨.

    @Override
    public ModelView process(Map<String, String> request) {
        return new ModelView("new-form");
    }
}
