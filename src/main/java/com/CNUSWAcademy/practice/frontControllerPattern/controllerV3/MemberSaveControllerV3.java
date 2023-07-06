package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import com.CNUSWAcademy.practice.frontControllerPattern.Member;
import com.CNUSWAcademy.practice.frontControllerPattern.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> request) {

        String userName = request.get("username");
        int age = Integer.parseInt(request.get("age"));

        Member member = new Member(userName, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.setAttribute("member", member);
        return modelView;
    }
}
