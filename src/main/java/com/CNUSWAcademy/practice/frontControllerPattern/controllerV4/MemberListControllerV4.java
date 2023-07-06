package com.CNUSWAcademy.practice.frontControllerPattern.controllerV4;

import com.CNUSWAcademy.practice.frontControllerPattern.Member;
import com.CNUSWAcademy.practice.frontControllerPattern.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> request, Map<String, Object> model) {

        List<Member> members = memberRepository.findAll();

        model.put("members", members);

        return "members";
    }
}
