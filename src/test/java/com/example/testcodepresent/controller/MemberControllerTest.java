package com.example.testcodepresent.controller;

import com.example.testcodepresent.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberControllerTest {
    @Autowired
    MemberController controller;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
//    @Rollback(false)
    @DisplayName("회원 가입한다")
    void join() {
        // given
        String nickname = "octoping";
        String email = "myc1365@naver.com";

        // when
        controller.join(nickname, email);

        // then
        assertTrue(memberRepository.existsMemberByEmail(email));
    }

    @Test
    @Transactional
    @DisplayName("회원가입할 때 이미 존재하는 이메일일 경우 실패한다")
    void join_DuplicateMember() {
        // given
        String nickname = "octoping";
        String email = "myc1365@naver.com";

        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.join(nickname, email);
            controller.join(nickname, email);
        });
    }
}