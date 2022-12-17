package com.example.testcodepresent.service;

import com.example.testcodepresent.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @MockBean
    EmailService emailService;

    @Test
    @DisplayName("회원가입 테스트 - Email을 Mocking한 후 email이 보내졌나 테스트")
    void joinTestByMocking() {
        // given
        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("Fake Email! " + Arrays.toString(args));
            return null;
        }).when(emailService).sendEmail(Mockito.anyString(), Mockito.anyString());

        String nickname = "octoping";
        String email = "myc1365@naver.com";

        // when
        memberService.join(nickname, email);

        // then
        Mockito.verify(emailService, Mockito.times(1)).sendEmail(email, "가입 완료!");
    }
}