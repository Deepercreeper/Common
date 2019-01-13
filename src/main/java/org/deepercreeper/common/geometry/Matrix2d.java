package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Matrix2d extends AbstractMatrix<Matrix2d> {
    public Matrix2d() {
        super(2, 2);
    }

    public Matrix2d(@NotNull double[]... values) {
        super(checkDimension(2, 2, values));
    }

    public Matrix2d(@NotNull AbstractMatrix<?> matrix) {
        super(matrix.checkDimension(2, 2));
    }

    @Override
    public double getDeterminant() {
        return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
    }

    @NotNull
    @Override
    public Matrix2d copy() {
        return new Matrix2d(this);
    }

    @NotNull
    @Override
    public Matrix2d create(int width, int height) {
        if (width != 2 || height != 2) {
            throw new IllegalArgumentException("Cannot create 2D matrix of dimension [" + width + ", " + height + "]");
        }
        return new Matrix2d();
    }

    @NotNull
    @Override
    public Matrix2d create(@NotNull double[]... values) {
        return new Matrix2d(values);
    }

    @NotNull
    @Override
    protected Matrix2d getThis() {
        return this;
    }

    @NotNull
    public static Matrix2d toRotationMatrix(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Matrix2d(new double[][]{{cos, sin}, {-sin, cos}});
    }
}
