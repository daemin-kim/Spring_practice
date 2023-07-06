package com.CNUSWAcademy.practice.frontControllerPattern.controllerV2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {
    
    // V1에서는 process의 반환 타입이 없었으나, 이제는 MyView를 반환함
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
