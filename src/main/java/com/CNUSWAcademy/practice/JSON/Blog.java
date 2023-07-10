package com.CNUSWAcademy.practice.JSON;

import java.util.ArrayList;
import java.util.List;

/*
  String 타입의 JSON 파일을 처리할 때
 */


public class Blog {

    private String address;
    private final List<String> visitor = new ArrayList<>();

    public List<String> getVisitor() {
        return visitor;
    }

    public String getAddress() {
        return address;
    }

    public Blog() {

    }
}
