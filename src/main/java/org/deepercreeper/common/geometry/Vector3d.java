package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

public class Vector3d extends AbstractVector<Vector3d> {
    public Vector3d() {
        super(3);
    }

    public Vector3d(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3d(@NotNull AbstractVector<?> vector) {
        super(vector.checkDimension(3));
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public double getZ() {
        return get(2);
    }

    public double getAbsX() {
        return getAbs(0);
    }

    public double getAbsY() {
        return getAbs(1);
    }

    public double getAbsZ() {
        return getAbs(2);
    }

    @NotNull
    public Vector3d setX(double x) {
        return set(0, x);
    }

    @NotNull
    public Vector3d setY(double y) {
        return set(1, y);
    }

    @NotNull
    public Vector3d setZ(double z) {
        return set(2, z);
    }

    @NotNull
    public Vector3d negateX() {
        return negate(0);
    }

    @NotNull
    public Vector3d negateY() {
        return negate(1);
    }

    @NotNull
    public Vector3d negateZ() {
        return negate(2);
    }

    @NotNull
    public Vector3d addX(double x) {
        return add(0, x);
    }

    @NotNull
    public Vector3d addY(double y) {
        return add(1, y);
    }

    @NotNull
    public Vector3d addZ(double z) {
        return add(2, z);
    }

    @NotNull
    public Vector3d subtractX(double x) {
        return subtract(0, x);
    }

    @NotNull
    public Vector3d subtractY(double y) {
        return addY(-y);
    }

    @NotNull
    public Vector3d subtractZ(double z) {
        return addZ(-z);
    }

    @NotNull
    public Vector3d multiplyX(double x) {
        return multiply(0, x);
    }

    @NotNull
    public Vector3d multiplyY(double y) {
        return multiply(1, y);
    }

    @NotNull
    public Vector3d multiplyZ(double z) {
        return multiply(2, z);
    }

    @NotNull
    public Vector3d divideX(double x) {
        return divide(0, x);
    }

    @NotNull
    public Vector3d divideY(double y) {
        return divide(1, y);
    }

    @NotNull
    public Vector3d divideZ(double z) {
        return divide(2, z);
    }

    @NotNull
    public Vector3d negativeX() {
        return negative(0);
    }

    @NotNull
    public Vector3d negativeY() {
        return negative(1);
    }

    @NotNull
    public Vector3d negativeZ() {
        return negative(2);
    }

    @NotNull
    public Vector3d withX(double x) {
        return with(0, x);
    }

    @NotNull
    public Vector3d withY(double y) {
        return with(1, y);
    }

    @NotNull
    public Vector3d withZ(double z) {
        return with(2, z);
    }

    @NotNull
    public Vector3d plusX(double x) {
        return plus(0, x);
    }

    @NotNull
    public Vector3d plusY(double y) {
        return plus(1, y);
    }

    @NotNull
    public Vector3d plusZ(double z) {
        return plus(2, z);
    }

    @NotNull
    public Vector3d minusX(double x) {
        return minus(0, x);
    }

    @NotNull
    public Vector3d minusY(double y) {
        return minus(1, y);
    }

    @NotNull
    public Vector3d minusZ(double z) {
        return minus(2, z);
    }

    @NotNull
    public Vector3d timesX(double x) {
        return times(0, x);
    }

    @NotNull
    public Vector3d timesY(double y) {
        return times(1, y);
    }

    @NotNull
    public Vector3d timesZ(double z) {
        return times(2, z);
    }

    @NotNull
    public Vector3d dividedByX(double x) {
        return dividedBy(0, x);
    }

    @NotNull
    public Vector3d dividedByY(double y) {
        return dividedBy(1, y);
    }

    @NotNull
    public Vector3d dividedByZ(double z) {
        return dividedBy(2, z);
    }

    @NotNull
    public Vector3d cross(@NotNull Vector3d vector) {
        return new Vector3d(getY() * vector.getZ() - getZ() * vector.getY(), getZ() * vector.getX() - getX() * vector.getZ(), getX() * vector.getY() - getY() * vector.getX());
    }

    @NotNull
    public Matrix3d star() {
        return new Matrix3d(new double[][]{{0, getZ(), -getY()}, {-getZ(), 0, getX()}, {getY(), -getX(), 0}});
    }

    @NotNull
    public Matrix3d toRotationMatrix() {
        double angle = norm();
        Vector3d n = normalized();
        double x = n.getX();
        double y = n.getY();
        double z = n.getZ();
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double oneMinusCos = 1 - cos;
        double xyCos = x * y * oneMinusCos;
        double xzCos = x * z * oneMinusCos;
        double yzCos = y * z * oneMinusCos;
        double xSin = x * sin;
        double ySin = y * sin;
        double zSin = z * sin;
        return new Matrix3d(new double[][]{
                {x * x * oneMinusCos + cos, xyCos + zSin, xzCos - ySin}, {xyCos - zSin, y * y * oneMinusCos, yzCos + xSin}, {xzCos + ySin, yzCos - xSin, z * z * oneMinusCos + cos}
        });
    }

    public boolean equalsX(double x) {
        return equals(0, x);
    }

    public boolean equalsY(double y) {
        return equals(1, y);
    }

    public boolean equalsZ(double z) {
        return equals(2, z);
    }

    @NotNull
    @Override
    public Vector3d copy() {
        return new Vector3d(this);
    }

    @NotNull
    @Override
    public Vector3d create(int dimension) {
        if (dimension != 3) {
            throw new IllegalArgumentException("Cannot create 3D vector of dimension " + dimension);
        }
        return new Vector3d();
    }

    @NotNull
    @Override
    protected Vector3d getThis() {
        return this;
    }
}
