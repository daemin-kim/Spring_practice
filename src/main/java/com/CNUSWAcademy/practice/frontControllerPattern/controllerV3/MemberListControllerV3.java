package com.CNUSWAcademy.practice.frontControllerPattern.controllerV3;

import com.CNUSWAcademy.practice.frontControllerPattern.Member;
import com.CNUSWAcademy.practice.frontControllerPattern.MemberRepository;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> request) {

        List<Member> members = memberRepository.findAll();

        ModelView modelView = new ModelView("members");
        modelView.setAttribute("members", members);
        return modelView;
    }
}
