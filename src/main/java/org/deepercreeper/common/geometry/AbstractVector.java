package org.deepercreeper.common.geometry;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public abstract class AbstractVector<V extends AbstractVector<V>> {
    private final double[] values;

    private final int dimension;

    protected AbstractVector(int dimension) {
        if (dimension < 0) {
            throw new IllegalArgumentException("Dimension has to be non negative: " + dimension);
        }
        this.dimension = dimension;
        values = new double[dimension];
    }

    protected AbstractVector(@NotNull double... values) {
        dimension = values.length;
        this.values = new double[dimension];
        System.arraycopy(values, 0, this.values, 0, dimension);
    }

    protected AbstractVector(@NotNull AbstractVector<?> vector) {
        this(vector.values);
    }

    public double get(int index) {
        return values[index];
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isZero() {
        return IntStream.range(0, getDimension()).mapToDouble(this::get).noneMatch(value -> value != 0);
    }

    public double getAbs(int index) {
        return Math.abs(get(index));
    }

    @NotNull
    public V set(int index, double value) {
        values[index] = value;
        return getThis();
    }

    @NotNull
    public V set(@NotNull double... values) {
        checkDimension(values);
        System.arraycopy(values, 0, this.values, 0, values.length);
        return getThis();
    }

    @NotNull
    public V set(@NotNull AbstractVector<?> vector) {
        return set(vector.values);
    }

    @NotNull
    public V negate(int index) {
        values[index] = -get(index);
        return getThis();
    }

    @NotNull
    public V negate() {
        map(value -> -value);
        return getThis();
    }

    @NotNull
    public V add(int index, double value) {
        values[index] += value;
        return getThis();
    }

    @NotNull
    public V add(@NotNull double... values) {
        checkDimension(values);
        map((index, value) -> value + values[index]);
        return getThis();
    }

    @NotNull
    public V add(@NotNull AbstractVector<?> vector) {
        return add(vector.values);
    }

    @NotNull
    public V subtract(int index, double value) {
        return add(index, -value);
    }

    @NotNull
    public V subtract(@NotNull double... values) {
        checkDimension(values);
        map((index, value) -> value - values[index]);
        return getThis();
    }

    @NotNull
    public V subtract(@NotNull AbstractVector<?> vector) {
        return subtract(vector.values);
    }

    @NotNull
    public V multiply(int index, double value) {
        values[index] *= value;
        return getThis();
    }

    @NotNull
    public V multiply(double factor) {
        map(value -> value * factor);
        return getThis();
    }

    @NotNull
    public V multiply(@NotNull double... values) {
        checkDimension(values);
        map((index, value) -> value * values[index]);
        return getThis();
    }

    @NotNull
    public V divide(int index, double value) {
        return multiply(index, 1 / value);
    }

    @NotNull
    public V divide(double factor) {
        return multiply(1 / factor);
    }

    @NotNull
    public V divide(@NotNull double... values) {
        checkDimension(values);
        map((index, value) -> value / values[index]);
        return getThis();
    }

    @NotNull
    public V normalize() {
        return divide(norm());
    }

    @NotNull
    public V absolute() {
        return copy().map(Math::abs);
    }

    @NotNull
    public V negative(int index) {
        return copy().negate(index);
    }

    @NotNull
    public V negative() {
        return copy().negate();
    }

    @NotNull
    public V with(int index, double value) {
        return copy().set(index, value);
    }

    @NotNull
    public V plus(int index, double value) {
        return copy().add(index, value);
    }

    @NotNull
    public V plus(@NotNull double... values) {
        return copy().add(values);
    }

    @NotNull
    public V plus(@NotNull AbstractVector<?> vector) {
        return copy().add(vector);
    }

    @NotNull
    public V minus(int index, double value) {
        return copy().subtract(index, value);
    }

    @NotNull
    public V minus(@NotNull double... values) {
        return copy().subtract(values);
    }

    @NotNull
    public V minus(@NotNull AbstractVector<?> vector) {
        return copy().subtract(vector);
    }

    @NotNull
    public V times(int index, double value) {
        return copy().multiply(index, value);
    }

    @NotNull
    public V times(double factor) {
        return copy().multiply(factor);
    }

    @NotNull
    public V times(@NotNull double... values) {
        return copy().multiply(values);
    }

    @NotNull
    public V dividedBy(int index, double value) {
        return copy().divide(index, value);
    }

    @NotNull
    public V dividedBy(double factor) {
        return copy().divide(factor);
    }

    @NotNull
    public V dividedBy(@NotNull double... values) {
        return copy().divide(values);
    }

    @NotNull
    public V normalized() {
        return copy().normalize();
    }

    public double norm() {
        return Math.sqrt(normSquared());
    }

    public double normSquared() {
        return stream().reduce(0, (identity, value) -> identity + Math.pow(value, 2));
    }

    public double dot(@NotNull AbstractVector<?> vector) {
        checkDimension(vector);
        double dot = 0;
        for (int index = 0; index < getDimension(); index++) {
            dot += get(index) * vector.get(index);
        }
        return dot;
    }

    @NotNull
    public V create() {
        return create(getDimension());
    }

    @NotNull
    public V map(@NotNull DoubleUnaryOperator mapping) {
        return map((index, value) -> mapping.applyAsDouble(value));
    }

    @NotNull
    public V map(@NotNull Operation mapping) {
        for (int index = 0; index < getDimension(); index++) {
            values[index] = mapping.apply(index, get(index));
        }
        return getThis();
    }

    public void forEach(@NotNull DoubleConsumer operation) {
        stream().forEach(operation);
    }

    @NotNull
    public DoubleStream stream() {
        return DoubleStream.of(values);
    }

    @NotNull
    public V checkDimension(@NotNull double[] values) {
        checkDimension(getDimension(), values);
        return getThis();
    }

    public static void checkDimension(int dimension, @NotNull double[] values) {
        if (values.length != dimension) {
            throw new IllegalArgumentException("Values have wrong dimension: " + values.length);
        }
    }

    @NotNull
    public V checkDimension(@NotNull AbstractVector<?> vector) {
        vector.checkDimension(getDimension());
        return getThis();
    }

    @NotNull
    public V checkDimension(int dimension) {
        if (getDimension() != dimension) {
            throw new IllegalArgumentException("Vector has wrong dimension: " + getDimension());
        }
        return getThis();
    }

    public boolean equals(int index, double value) {
        return get(index) == value;
    }

    public boolean equals(@NotNull double... values) {
        checkDimension(values);
        return Arrays.equals(this.values, values);
    }

    public boolean equals(@NotNull AbstractVector<?> vector, double error) {
        return minus(vector).norm() < error;
    }

    public boolean equals(@NotNull AbstractVector<?> vector) {
        return equals(vector.values);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractVector<?> && equals((AbstractVector<?>) obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    @NotNull
    public abstract V copy();

    @NotNull
    public abstract V create(int dimension);

    @NotNull
    protected abstract V getThis();

    @FunctionalInterface
    public interface Operation {
        double apply(int index, double value);
    }
}
