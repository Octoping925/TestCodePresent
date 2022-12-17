package com.example.testcodepresent.domain;

import com.example.testcodepresent.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @ParameterizedTest
    @CsvSource({"10,10", "50,50", "100,0", "150,50"})
    @DisplayName("huntMonster 메소드로 경험치를 획득한다")
    void huntMonster(int exp, int expected) {
        // given
        Member member = new Member();

        // when
        member.huntMonster(exp, LocalDateTime.of(2022, 12, 17, 0, 0));

        // then
        Assertions.assertEquals(expected, member.getExp());
    }

    @ParameterizedTest
    @CsvSource({"10,20", "50,0", "100,0"})
    @DisplayName("일요일에는 경험치가 2배")
    void huntMonster_onSunday(int exp, int expected) {
        // given
        Member member = new Member();

        // when
        member.huntMonster(exp, LocalDateTime.of(2022, 12, 18, 0, 0));

        // then
        Assertions.assertEquals(expected, member.getExp());
    }
}