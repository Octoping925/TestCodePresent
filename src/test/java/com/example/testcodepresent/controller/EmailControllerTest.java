package com.example.testcodepresent.controller;

import com.example.testcodepresent.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// 원하는 빈만 등록해서 테스트
@SpringBootTest(classes = {EmailController.class, EmailService.class})
class EmailControllerTest {
    @Autowired
    EmailController controller;

    @Test
    @DisplayName("이메일 보내기 테스트")
    void sendEmail() {
        //given
        String email = "myc1365@naver.com";
        String context = "hi";

        // when
        String result = controller.sendEmail(email, context);

        // then
        assertEquals("Sent Email Successfully", result);
    }
}