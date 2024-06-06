package com.example.atividades.atividade13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactorialTest {

    private final Factorial factorial = new Factorial();

    @Test
    public void testCalculate_ValidInput() {
        assertEquals(1, factorial.calculate(0));
        assertEquals(1, factorial.calculate(1));
        assertEquals(2, factorial.calculate(2));
        assertEquals(6, factorial.calculate(3));
        assertEquals(24, factorial.calculate(4));
        assertEquals(120, factorial.calculate(5));
    }

    @Test
    public void testCalculate_LargeNumber() {
        assertEquals(3628800, factorial.calculate(10));
    }

    @Test
    public void testCalculate_NegativeInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factorial.calculate(-1);
        });

        assertEquals("Number must be non-negative", exception.getMessage());
    }

    @Test
    public void testCalculate_EdgeCaseZero() {
        assertEquals(1, factorial.calculate(0));
    }
}
