package com.example.testcodepresent.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    int x, y;
    Calculator c;

    @BeforeEach
    void init() {
        x = 6;
        y = 3;
        c = new Calculator();
    }

    @Test
    @DisplayName("두 숫자를 더한다")
    void add() {
        // given
        // when
        int result = c.add(x, y);

        // then
        assertEquals(x + y, result);
    }

    @Test
    @DisplayName("두 숫자를 뺀다")
    void subtract() {
        // given
        // when
        int result = c.subtract(x, y);

        // then
        assertEquals(x - y, result);
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,3", "10000,10000", "100000,100000"})
    @DisplayName("두 숫자를 곱한다")
    void multiply(int x, int y) {
        // given
        // when
        long result = c.multiply(x, y);

        // then
        System.out.println("result = " + result);
        assertEquals((long)x * y, result);
    }

    @Test
    @DisplayName("두 숫자를 나눈다")
    void divide() {
        // given
        // when
        int result = c.divide(x, y);

        // then
        assertEquals(x / y, result);
    }
}