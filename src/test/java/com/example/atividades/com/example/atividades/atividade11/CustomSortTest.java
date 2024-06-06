package com.example.atividades.atividade11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CustomSortTest {

    private CustomSort customSort = new CustomSort();

    @Test
    public void testCustomSort_ValidList(){

        List <Integer> reversedList = customSort.customSort(Arrays.asList(10,20,30,40,50));
        assertEquals(Arrays.asList(50,40,30,20,10), reversedList);

    }


    @Test
    public void testCustomSort_NegativeNumbersDescending(){

        List <Integer> reversedList = customSort.customSort(Arrays.asList(-50,-60,-70,-80,-90,-100));

        assertNotEquals(Arrays.asList(-100,-90,-80,-70,-60,-50), reversedList);

        assertEquals(Arrays.asList(-50,-60,-70,-80,-90,-100), reversedList);

    }


    @Test
    public void testCustomSort_NegativeNumbersAscending(){

        List <Integer> reversedList = customSort.customSort(Arrays.asList(-100,-90,-80,-70,-60,-50));

        assertEquals(Arrays.asList(-50,-60,-70,-80,-90,-100), reversedList);

    }





}
