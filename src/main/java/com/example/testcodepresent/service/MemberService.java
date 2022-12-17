package com.example.testcodepresent.service;

import com.example.testcodepresent.domain.Member;
import com.example.testcodepresent.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final EmailService emailService;

    public Member join(String nickname, String email) {
        if(memberRepository.existsMemberByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }

        Member member = new Member(nickname, email);
        memberRepository.save(member);

        emailService.sendEmail(email, "가입 완료!");

        return member;
    }

    public Member findMember(Long id) {
        return memberRepository.findMemberById(id);
    }
}
