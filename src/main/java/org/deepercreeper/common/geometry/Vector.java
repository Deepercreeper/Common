package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Vector extends AbstractVector<Vector> {
    public Vector(int dimension) {
        super(dimension);
    }

    public Vector(@NotNull double... values) {
        super(values);
    }

    public Vector(@NotNull AbstractVector<?> vector) {
        super(vector);
    }

    @NotNull
    @Override
    public Vector copy() {
        return new Vector(this);
    }

    @NotNull
    @Override
    public Vector create(int dimension) {
        return new Vector(dimension);
    }

    @NotNull
    @Override
    protected Vector getThis() {
        return this;
    }
}
