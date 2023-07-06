package com.CNUSWAcademy.practice.frontControllerPattern.controllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {
    @Override
    public String process(Map<String, String> request, Map<String, Object> model) {
        return "new-form";
    }
}
