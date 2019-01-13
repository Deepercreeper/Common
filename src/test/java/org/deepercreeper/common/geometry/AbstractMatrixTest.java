package org.deepercreeper.common.geometry;

import org.deepercreeper.common.pairs.Pair;
import org.junit.Assert;
import org.junit.Test;

public class AbstractMatrixTest {
    @Test
    public void testDecomposeLU() {
        Matrix matrix = new Matrix(new double[][]{{2, -4, 6, 4}, {4, -7, 8, 9}, {3, -5, 2, -2}, {5, -8, 9, 14}});
        System.out.println("A: " + matrix);
        Pair<Matrix, Matrix> lu = matrix.decomposeLU();
        System.out.println("L: " + lu.getKey());
        System.out.println("U: " + lu.getValue());
        Matrix product = lu.getKey().times(lu.getValue());
        System.out.println("LU: " + product);
        Assert.assertEquals(matrix, product);
    }

    @Test
    public void testInvertL() {
        Matrix l = new Matrix(new double[][]{{1, -2, 3, 2}, {0, 1, -4, 1}, {0, 0, 1, 3}, {0, 0, 0, 1}});
        System.out.println("L: " + l);
        Matrix inverse = AbstractMatrix.invertL(l);
        System.out.println("L^-1: " + inverse);
        Matrix one = l.times(inverse);
        System.out.println("E: " + one);
        Assert.assertEquals(l.createOne(), one);
    }

    @Test
    public void testInvertU() {
        Matrix u = new Matrix(new double[][]{{2, 0, 0, 0}, {4, 1, 0, 0}, {3, 1, -3, 0}, {5, 2, 2, -4}});
        System.out.println("U: " + u);
        Matrix inverse = AbstractMatrix.invertU(u);
        System.out.println("U^-1: " + inverse);
        Matrix one = u.times(inverse);
        System.out.println("E: " + one);
        Assert.assertEquals(u.createOne(), one);
    }

    @Test
    public void testInvert() {
        Matrix matrix = new Matrix(new double[][]{{2, -4, 6, 4}, {4, -7, 8, 9}, {3, -5, 2, -2}, {5, -8, 9, 14}});
        Matrix inverse = matrix.inverse();
        Matrix one = matrix.times(inverse);
        Matrix zero = one.minus(one.createOne());
        System.out.println("A: " + matrix);
        System.out.println("A^-1: " + inverse);
        System.out.println("E: " + one);
        System.out.println("0: " + zero);
        System.out.println("|0|: " + zero.norm());
        Assert.assertTrue(zero.norm() < 1e-10);
    }

    @Test
    public void testSlice() {
        Matrix matrix = new Matrix(new double[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}});
        Assert.assertEquals(new Matrix(new double[][]{{5, 8}, {6, 9}}), matrix.slice(0, 0));
        Assert.assertEquals(new Matrix(new double[][]{{2, 8}, {3, 9}}), matrix.slice(0, 1));
        Assert.assertEquals(new Matrix(new double[][]{{2, 5}, {3, 6}}), matrix.slice(0, 2));
        Assert.assertEquals(new Matrix(new double[][]{{4, 7}, {6, 9}}), matrix.slice(1, 0));
        Assert.assertEquals(new Matrix(new double[][]{{1, 7}, {3, 9}}), matrix.slice(1, 1));
        Assert.assertEquals(new Matrix(new double[][]{{1, 4}, {3, 6}}), matrix.slice(1, 2));
        Assert.assertEquals(new Matrix(new double[][]{{4, 7}, {5, 8}}), matrix.slice(2, 0));
        Assert.assertEquals(new Matrix(new double[][]{{1, 7}, {2, 8}}), matrix.slice(2, 1));
        Assert.assertEquals(new Matrix(new double[][]{{1, 4}, {2, 5}}), matrix.slice(2, 2));

        Assert.assertEquals(1, matrix.slice(new int[]{0}, new int[]{0}).get(0, 0), 0);
        Assert.assertEquals(2, matrix.slice(new int[]{1}, new int[]{0}).get(0, 0), 0);
        Assert.assertEquals(3, matrix.slice(new int[]{2}, new int[]{0}).get(0, 0), 0);
        Assert.assertEquals(4, matrix.slice(new int[]{0}, new int[]{1}).get(0, 0), 0);
        Assert.assertEquals(5, matrix.slice(new int[]{1}, new int[]{1}).get(0, 0), 0);
        Assert.assertEquals(6, matrix.slice(new int[]{2}, new int[]{1}).get(0, 0), 0);
        Assert.assertEquals(7, matrix.slice(new int[]{0}, new int[]{2}).get(0, 0), 0);
        Assert.assertEquals(8, matrix.slice(new int[]{1}, new int[]{2}).get(0, 0), 0);
        Assert.assertEquals(9, matrix.slice(new int[]{2}, new int[]{2}).get(0, 0), 0);

        Assert.assertTrue(matrix.slice(new int[]{0, 1, 2}, new int[0]).isEmpty());
        Assert.assertTrue(matrix.slice(new int[0], new int[]{0, 1, 2}).isEmpty());
    }

    @Test
    public void testDeterminant() {
        Matrix matrix = new Matrix(new double[][]{{-2, -1, 2}, {2, 1, 0}, {-3, 3, -1}});
        System.out.println("A: " + matrix);
        double determinant = matrix.getDeterminant();
        System.out.println("det(A): " + determinant);
        Assert.assertEquals(18, determinant, 0);
    }
}