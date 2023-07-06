package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import java.util.Map;

/*
    HTTPServletRequest 대신 Map을 통해 Request Parameter를 받으며, 반환 타입을 MyView에서 ModelView로 변경함
 */

public interface ControllerV3 {
    ModelView process(Map<String, String> request);
}
