package com.example.testcodepresent.service;

import com.example.testcodepresent.domain.Member;
import com.example.testcodepresent.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberServiceWithNoSpringFrameworkTest {
    String nickname = "octoping";
    String email = "myc1365@naver.com";

    @Test
    @DisplayName("회원 가입 테스트 - Email을 Mocking")
    void joinTestByMocking() {
        // given
        MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
        EmailService emailService = Mockito.mock(EmailService.class);

        MemberService service = new MemberService(memberRepository, emailService);

        // when
        Member member = service.join(nickname, email);

        // then
        Assertions.assertNotNull(member);
    }

    @Test
    @DisplayName("회원가입 테스트 - Email을 Stubbing")
    void joinTestByStubbing() {
        // given
        class FakeEmailService extends EmailService {
            @Override
            public void sendEmail(String email, String context) {
                System.out.println("Fake Email Sent! " + email + " " + context);
            }
        }

        FakeEmailService emailService = new FakeEmailService();
        MemberService memberService = new MemberService(Mockito.mock(MemberRepository.class), emailService);

        // when
        memberService.join(nickname, email);
    }

}