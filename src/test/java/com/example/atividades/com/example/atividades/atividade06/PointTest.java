package com.example.atividades.atividade06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PointTest {

    @Test
    public void testConstructor() {
        Point p = new Point(3.0, 4.0);
        assertNotNull(p);
    }

    @Test
    public void testDistanceTo_ValidPoints() {
        Point p1 = new Point(3.0, 4.0);
        Point p2 = new Point(0.0, 0.0);
        double distance = p1.distanceTo(p2);
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testDistanceTo_SamePoint() {
        Point p1 = new Point(3.0, 4.0);
        double distance = p1.distanceTo(p1);
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    public void testDistanceTo_NegativeCoordinates() {
        Point p1 = new Point(-3.0, -4.0);
        Point p2 = new Point(0.0, 0.0);
        double distance = p1.distanceTo(p2);
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testDistanceTo_DecimalCoordinates() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(4.0, 6.0);
        double distance = p1.distanceTo(p2);
        assertEquals(4.301, distance, 0.001);
    }

      @Test
    public void testDistanceTo_NullPoint() {
        Point p1 = new Point(3.0, 4.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            p1.distanceTo(null);
        });
        assertEquals("Argument must be a Point", exception.getMessage());
    }
}

