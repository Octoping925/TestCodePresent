package com.example.testcodepresent.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String nickname;
    String email;
    int level;
    int exp;

    LocalDateTime joinDate;

    protected Member() {}

    public Member(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        this.level = 1;
        this.exp = 0;
        this.joinDate = LocalDateTime.now();
    }

    public void huntMonster(int monsterExp, LocalDateTime now) {
        if(now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            monsterExp *= 2;
        }

        this.exp += monsterExp;

        if(this.exp >= 100) {
            this.level += (this.exp / 100);
            exp %= 100;
        }
    }
}
