package com.CNUSWAcademy.practice.frontControllerPattern.controllerV4;

import com.CNUSWAcademy.practice.frontControllerPattern.Member;
import com.CNUSWAcademy.practice.frontControllerPattern.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> request, Map<String, Object> model) {

        String userName = request.get("username");
        int age = Integer.parseInt(request.get("age"));

        Member member = new Member(userName, age);
        memberRepository.save(member);

        model.put("member", member);

        return "save-result";
    }
}
