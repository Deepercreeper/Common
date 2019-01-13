package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Matrix extends AbstractMatrix<Matrix> {
    public Matrix(int width, int height) {
        super(width, height);
    }

    public Matrix(@NotNull double[]... values) {
        super(values);
    }

    public Matrix(@NotNull AbstractMatrix<?> matrix) {
        super(matrix);
    }

    @NotNull
    @Override
    public Matrix copy() {
        return new Matrix(this);
    }

    @NotNull
    @Override
    public Matrix create(int width, int height) {
        return new Matrix(width, height);
    }

    @NotNull
    @Override
    public Matrix create(@NotNull double[]... values) {
        return new Matrix(values);
    }

    @NotNull
    @Override
    protected Matrix getThis() {
        return this;
    }
}
