package com.example.atividades.atividade08;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    private final Statistics statistics = new Statistics();

    @Test
    public void testCalculateAverage_ValidList() {
        double average = statistics.calculateAverage(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(3.0, average, 0.001);
    }

    @Test
    public void testCalculateAverage_SingleElement() {
        double average = statistics.calculateAverage(Collections.singletonList(10));
        assertEquals(10.0, average, 0.001);
    }

    @Test
    public void testCalculateAverage_NegativeNumbers() {
        double average = statistics.calculateAverage(Arrays.asList(-1, -2, -3, -4, -5));
        assertEquals(-3.0, average, 0.001);
    }

    @Test
    public void testCalculateAverage_MixedNumbers() {
        double average = statistics.calculateAverage(Arrays.asList(-1, 0, 1));
        assertEquals(0.0, average, 0.001);
    }

    @Test
    public void testCalculateAverage_EmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            statistics.calculateAverage(Collections.emptyList());
        });

        assertEquals("The list of numbers cannot be empty", exception.getMessage());
    }

    @Test
    public void testCalculateAverage_NullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            statistics.calculateAverage(null);
        });

        assertEquals("The list of numbers cannot be empty", exception.getMessage());
    }
}
