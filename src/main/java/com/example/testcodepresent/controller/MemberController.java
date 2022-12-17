package com.example.testcodepresent.controller;

import com.example.testcodepresent.domain.Member;
import com.example.testcodepresent.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public Long join(@RequestParam String nickname, @RequestParam String email) {
        Member member = memberService.join(nickname, email);
        return member.getId();
    }

    @GetMapping("/info/{memberId}")
    public Member info(@PathVariable(name = "memberId") Long memberId) {
        return memberService.findMember(memberId);
    }

}
