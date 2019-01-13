package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Vector2d extends AbstractVector<Vector2d> {
    public Vector2d() {
        super(2);
    }

    public Vector2d(double x, double y) {
        super(x, y);
    }

    public Vector2d(@NotNull AbstractVector<?> vector) {
        super(vector.checkDimension(2));
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public double getAbsX() {
        return getAbs(0);
    }

    public double getAbsY() {
        return getAbs(1);
    }

    @NotNull
    public Vector2d setX(double x) {
        return set(0, x);
    }

    @NotNull
    public Vector2d setY(double y) {
        return set(1, y);
    }

    @NotNull
    public Vector2d negateX() {
        return negate(0);
    }

    @NotNull
    public Vector2d negateY() {
        return negate(1);
    }

    @NotNull
    public Vector2d addX(double x) {
        return add(0, x);
    }

    @NotNull
    public Vector2d addY(double y) {
        return add(1, y);
    }

    @NotNull
    public Vector2d subtractX(double x) {
        return subtract(0, x);
    }

    @NotNull
    public Vector2d subtractY(double y) {
        return addY(-y);
    }

    @NotNull
    public Vector2d multiplyX(double x) {
        return multiply(0, x);
    }

    @NotNull
    public Vector2d multiplyY(double y) {
        return multiply(1, y);
    }

    @NotNull
    public Vector2d divideX(double x) {
        return divide(0, x);
    }

    @NotNull
    public Vector2d divideY(double y) {
        return divide(1, y);
    }

    @NotNull
    public Vector2d negativeX() {
        return negative(0);
    }

    @NotNull
    public Vector2d negativeY() {
        return negative(1);
    }

    @NotNull
    public Vector2d withX(double x) {
        return with(0, x);
    }

    @NotNull
    public Vector2d withY(double y) {
        return with(1, y);
    }

    @NotNull
    public Vector2d plusX(double x) {
        return plus(0, x);
    }

    @NotNull
    public Vector2d plusY(double y) {
        return plus(1, y);
    }

    @NotNull
    public Vector2d minusX(double x) {
        return minus(0, x);
    }

    @NotNull
    public Vector2d minusY(double y) {
        return minus(1, y);
    }

    @NotNull
    public Vector2d timesX(double x) {
        return times(0, x);
    }

    @NotNull
    public Vector2d timesY(double y) {
        return times(1, y);
    }

    @NotNull
    public Vector2d dividedByX(double x) {
        return dividedBy(0, x);
    }

    @NotNull
    public Vector2d dividedByY(double y) {
        return dividedBy(1, y);
    }

    public boolean equalsX(double x) {
        return equals(0, x);
    }

    public boolean equalsY(double y) {
        return equals(1, y);
    }

    @NotNull
    @Override
    public Vector2d copy() {
        return new Vector2d(this);
    }

    @NotNull
    @Override
    public Vector2d create(int dimension) {
        if (dimension != 3) {
            throw new IllegalArgumentException("Cannot create 3D vector of dimension " + dimension);
        }
        return new Vector2d();
    }

    @NotNull
    @Override
    protected Vector2d getThis() {
        return this;
    }
}
