package org.deepercreeper.common.geometry;

import org.junit.Test;

public class MatrixTest {
    @Test
    public void testDisplay() {
        System.out.println("String:");
        System.out.println(new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));

        System.out.println("Display:");
        System.out.println(new Matrix(new double[][]{{1.000001, 2, 3}, {4.004, 5, 6}, {7.9999999, 8, 9}}).display());
    }
}