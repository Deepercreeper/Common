package org.deepercreeper.common.geometry;

import org.junit.Assert;
import org.junit.Test;

public class Matrix3dTest {
    @Test
    public void testDeterminant() {
        Matrix3d matrix = new Matrix3d(new double[][]{{-2, -1, 2}, {2, 1, 0}, {-3, 3, -1}});
        System.out.println("A: " + matrix);
        double determinant = matrix.getDeterminant();
        System.out.println("det(A): " + determinant);
        Assert.assertEquals(18, determinant, 0);
    }
}