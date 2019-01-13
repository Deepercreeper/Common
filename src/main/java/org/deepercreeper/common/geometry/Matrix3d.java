package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Matrix3d extends AbstractMatrix<Matrix3d> {
    public Matrix3d() {
        super(3, 3);
    }

    public Matrix3d(@NotNull double[]... values) {
        super(checkDimension(3, 3, values));
    }

    public Matrix3d(@NotNull AbstractMatrix<?> matrix) {
        super(matrix.checkDimension(3, 3));
    }

    @Override
    public double getDeterminant() {
        double determinant = 0;
        determinant += get(0, 0) * get(1, 1) * get(2, 2);
        determinant += get(1, 0) * get(2, 1) * get(0, 2);
        determinant += get(2, 0) * get(0, 1) * get(1, 2);
        determinant -= get(0, 2) * get(1, 1) * get(2, 0);
        determinant -= get(1, 2) * get(2, 1) * get(0, 0);
        determinant -= get(2, 2) * get(0, 1) * get(1, 0);
        return determinant;
    }

    @NotNull
    @Override
    public Matrix3d copy() {
        return new Matrix3d(this);
    }

    @NotNull
    @Override
    public Matrix3d create(int width, int height) {
        if (width != 3 || height != 3) {
            throw new IllegalArgumentException("Cannot create 3D matrix of dimension [" + width + ", " + height + "]");
        }
        return new Matrix3d();
    }

    @NotNull
    @Override
    public Matrix3d create(@NotNull double[]... values) {
        return new Matrix3d(values);
    }

    @NotNull
    @Override
    protected Matrix3d getThis() {
        return this;
    }
}
