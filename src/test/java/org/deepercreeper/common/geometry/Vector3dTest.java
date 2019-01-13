package org.deepercreeper.common.geometry;

import org.junit.Assert;
import org.junit.Test;

public class Vector3dTest {
    @Test
    public void testToRotationMatrix() {
        Vector3d vector = new Vector3d(Math.PI / 2, 0, 0);
        Matrix3d xRotation = new Matrix3d(new double[][]{{1, 0, 0}, {0, 0, 1}, {0, -1, 0}});
        Assert.assertTrue(xRotation.equals(vector.toRotationMatrix(), 1e-10));

        vector = new Vector3d(0, Math.PI / 2, 0);
        Matrix3d yRotation = new Matrix3d(new double[][]{{0, 0, -1}, {0, 1, 0}, {1, 0, 0}});
        Assert.assertTrue(yRotation.equals(vector.toRotationMatrix(), 1e-10));

        vector = new Vector3d(0, 0, Math.PI / 2);
        Matrix3d zRotation = new Matrix3d(new double[][]{{0, 1, 0}, {-1, 0, 0}, {0, 0, 1}});
        Assert.assertTrue(zRotation.equals(vector.toRotationMatrix(), 1e-10));

        vector = new Vector3d(0, Math.PI, 0);
        System.out.println(vector);
        System.out.println(vector.toRotationMatrix().display());
        System.out.println(vector.toRotationMatrix().toRotationVector());
        Assert.assertTrue(vector.toRotationMatrix().toRotationVector().equals(vector, 1e-10));
    }
}