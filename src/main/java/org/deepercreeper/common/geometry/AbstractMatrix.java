package org.deepercreeper.common.geometry;

import org.deepercreeper.common.pairs.ImmutablePair;
import org.deepercreeper.common.pairs.Pair;
import org.deepercreeper.common.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractMatrix<M extends AbstractMatrix<M>> {
    private final double[][] values;

    private final int width;

    private final int height;

    protected AbstractMatrix(int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("Width has to be non negative: " + width);
        }
        if (height < 0) {
            throw new IllegalArgumentException("Height has to be non negative: " + height);
        }
        if (width == 0 || height == 0) {
            width = height = 0;
        }
        this.width = width;
        this.height = height;
        values = new double[width][height];
    }

    protected AbstractMatrix(@NotNull double[]... values) {
        int[] size = check(values);
        width = size[0];
        height = size[1];
        this.values = new double[width][height];
        for (int i = 0; i < width; i++) {
            System.arraycopy(values[i], 0, this.values[i], 0, height);
        }
    }

    protected AbstractMatrix(@NotNull AbstractMatrix<?> matrix) {
        width = matrix.getWidth();
        height = matrix.getHeight();
        values = new double[width][height];
        for (int i = 0; i < width; i++) {
            System.arraycopy(matrix.values[i], 0, values[i], 0, height);
        }
    }

    public double get(int x, int y) {
        return values[x][y];
    }

    @NotNull
    public Vector getRow(int y) {
        double[] values = new double[getWidth()];
        for (int x = 0; x < getWidth(); x++) {
            values[x] = get(x, y);
        }
        return new Vector(values);
    }

    @NotNull
    public Vector[] getRows() {
        Vector[] rows = new Vector[getHeight()];
        for (int y = 0; y < getHeight(); y++) {
            rows[y] = getRow(y);
        }
        return rows;
    }

    @NotNull
    public Vector getColumn(int x) {
        double[] values = new double[getHeight()];
        System.arraycopy(this.values[x], 0, values, 0, getHeight());
        return new Vector(values);
    }

    @NotNull
    public Vector[] getColumns() {
        Vector[] columns = new Vector[getWidth()];
        for (int x = 0; x < getWidth(); x++) {
            columns[x] = getColumn(x);
        }
        return columns;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isEmpty() {
        return getWidth() == 0;
    }

    public double getAbs(int x, int y) {
        return Math.abs(get(x, y));
    }

    @NotNull
    public M set(int x, int y, double value) {
        values[x][y] = value;
        return getThis();
    }

    @NotNull
    public M setRow(int y, @NotNull double... values) {
        checkWidth(values);
        return mapRow(y, (x, i, value) -> values[x]);
    }

    @NotNull
    public M setRow(int y, @NotNull AbstractVector<?> vector) {
        checkHeight(vector);
        return mapRow(y, (x, i, value) -> vector.get(x));
    }

    @NotNull
    public M setColumn(int x, @NotNull double... values) {
        checkHeight(values);
        System.arraycopy(values, 0, this.values[x], 0, getHeight());
        return getThis();
    }

    @NotNull
    public M setColumn(int x, @NotNull AbstractVector<?> vector) {
        checkWidth(vector);
        return mapColumn(x, (i, y, value) -> vector.get(y));
    }

    @NotNull
    public M set(@NotNull double[]... values) {
        checkDimension(values);
        for (int i = 0; i < getWidth(); i++) {
            System.arraycopy(values[i], 0, this.values[i], 0, getHeight());
        }
        return getThis();
    }

    @NotNull
    public M set(@NotNull AbstractMatrix<?> matrix) {
        return set(matrix.values);
    }

    @NotNull
    public M negate(int x, int y) {
        values[x][y] = -get(x, y);
        return getThis();
    }

    @NotNull
    public M negate() {
        return map(value -> -value);
    }

    @NotNull
    public M add(int x, int y, double value) {
        values[x][y] += value;
        return getThis();
    }

    @NotNull
    public M addRow(int y, @NotNull double... values) {
        checkWidth(values);
        return mapRow(y, (x, i, value) -> value + values[x]);
    }

    @NotNull
    public M addRow(int y, @NotNull AbstractVector<?> vector) {
        checkWidth(vector);
        return mapRow(y, (x, i, value) -> value + vector.get(x));
    }

    @NotNull
    public M addColumn(int x, @NotNull double... values) {
        checkHeight(values);
        return mapColumn(x, (i, y, value) -> value + values[y]);
    }

    @NotNull
    public M addColumn(int x, @NotNull AbstractVector<?> vector) {
        checkHeight(vector);
        return mapColumn(x, (i, y, value) -> value + vector.get(y));
    }

    @NotNull
    public M add(@NotNull double[]... values) {
        checkDimension(values);
        return map((x, y, value) -> value + values[x][y]);
    }

    @NotNull
    public M add(@NotNull AbstractMatrix<?> matrix) {
        return add(matrix.values);
    }

    @NotNull
    public M subtract(int x, int y, double value) {
        return add(x, y, -value);
    }

    @NotNull
    public M subtractRow(int y, @NotNull double... values) {
        checkWidth(values);
        return mapRow(y, (x, i, value) -> value - values[x]);
    }

    @NotNull
    public M subtractRow(int y, @NotNull AbstractVector<?> vector) {
        checkWidth(vector);
        return mapRow(y, (x, i, value) -> value - vector.get(x));
    }

    @NotNull
    public M subtractColumn(int x, @NotNull double... values) {
        checkHeight(values);
        return mapColumn(x, (i, y, value) -> value - values[y]);
    }

    @NotNull
    public M subtractColumn(int x, @NotNull AbstractVector<?> vector) {
        checkHeight(vector);
        return mapColumn(x, (i, y, value) -> value - vector.get(y));
    }

    @NotNull
    public M subtract(@NotNull double[]... values) {
        checkDimension(values);
        return map((x, y, value) -> value - values[x][y]);
    }

    @NotNull
    public M subtract(@NotNull AbstractMatrix<?> matrix) {
        return subtract(matrix.values);
    }

    @NotNull
    public M multiply(int x, int y, double value) {
        values[x][y] *= value;
        return getThis();
    }

    @NotNull
    public M multiply(double factor) {
        return map(value -> value * factor);
    }

    @NotNull
    public M multiplyRow(int y, double factor) {
        return mapRow(y, value -> value * factor);
    }

    @NotNull
    public M multiplyColumn(int x, double factor) {
        return mapColumn(x, value -> value * factor);
    }

    @NotNull
    public M multiply(@NotNull double[]... values) {
        checkDimension(values);
        return map((x, y, value) -> value * values[x][y]);
    }

    @NotNull
    public M multiply(@NotNull AbstractMatrix<?> matrix) {
        matrix.checkDimension(getWidth(), getWidth());
        return set(times(matrix));
    }

    @NotNull
    public M multiplyLeft(@NotNull AbstractMatrix<?> matrix) {
        matrix.checkDimension(getHeight(), getHeight());
        return set(matrix.times(this));
    }

    @NotNull
    public M divide(int x, int y, double value) {
        return multiply(x, y, 1 / value);
    }

    @NotNull
    public M divide(double factor) {
        return multiply(1 / factor);
    }

    @NotNull
    public M divideRow(int y, double factor) {
        return multiplyRow(y, 1 / factor);
    }

    @NotNull
    public M divideColumn(int x, double factor) {
        return multiplyColumn(x, 1 / factor);
    }

    @NotNull
    public M divide(@NotNull double[]... values) {
        checkDimension(values);
        return map((x, y, value) -> value / values[x][y]);
    }

    @NotNull
    public M normalize() {
        return divide(norm());
    }

    @NotNull
    public M square() {
        return set(squared());
    }

    @NotNull
    public M pow(int n) {
        return set(toPow(n));
    }

    @NotNull
    public M transpose() {
        checkHeight(getWidth());
        return set(transposed());
    }

    public boolean isInvertible() {
        return !isEmpty() && getWidth() == getHeight() && getDeterminant() != 0;
    }

    public double getDeterminant() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot compute determinant of empty matrix");
        }
        checkHeight(getWidth());
        if (getWidth() == 1) {
            return get(0, 0);
        }
        if (getWidth() == 2) {
            return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
        }
        double determinant = 0;
        for (int x = 0; x < getWidth(); x++) {
            int signum = x % 2 == 0 ? -1 : 1;
            determinant += signum * get(x, 0) * slice(x, 0).getDeterminant();
        }
        return determinant;
    }

    @NotNull
    public M slice(int removedColumn, int removedRow) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot slice from empty matrix");
        }
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        double[][] values = new double[width][height];
        int column = 0;
        for (int x = 0; x < getWidth(); x++) {
            if (x == removedColumn) {
                continue;
            }
            if (removedRow > 0) {
                System.arraycopy(this.values[x], 0, values[column], 0, removedRow);
            }
            if (removedRow < height) {
                System.arraycopy(this.values[x], removedRow + 1, values[column], removedRow, height - removedRow);
            }
            column++;
        }
        return create(values);
    }

    @NotNull
    public M slice(@NotNull int[] columns, @NotNull int[] rows) {
        if (isEmpty()) {
            throw new IllegalArgumentException("Cannot slice from empty matrix");
        }
        int width = columns.length;
        int height = rows.length;
        if (width == 0 || height == 0) {
            return create(0, 0);
        }
        double[][] values = new double[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                values[x][y] = get(columns[x], rows[y]);
            }
        }
        return create(values);
    }

    @NotNull
    public M invert() {
        Pair<M, M> lu = decomposeLU();
        return set(invertU(lu.getValue()).multiply(invertL(lu.getKey())));
    }

    @NotNull
    public static <M extends AbstractMatrix<M>> M invertL(@NotNull M l) {
        M inverse = l.createOne();
        int n = l.getWidth();
        for (int column = 0; column < n - 1; column++) {
            for (int row = column + 1; row < n; row++) {
                double value = l.get(column, row);
                for (int x = 0; x <= column; x++) {
                    inverse.subtract(x, row, value * inverse.get(x, column));
                }
            }
        }
        return inverse;
    }

    @NotNull
    public static <M extends AbstractMatrix<M>> M invertU(@NotNull M u) {
        M copy = u.copy();
        M inverse = copy.createOne();
        int n = copy.getWidth();
        for (int column = 0; column < n; column++) {
            double value = copy.get(column, column);
            inverse.divide(column, column, value);
            for (int x = column + 1; x < n; x++) {
                copy.divide(x, column, value);
            }
        }
        for (int column = 1; column < n; column++) {
            for (int row = 0; row < column; row++) {
                double value = copy.get(column, row);
                inverse.subtract(column, row, value * inverse.get(column, column));
                for (int x = column + 1; x < n; x++) {
                    copy.subtract(x, row, value * copy.get(x, column));
                }
            }
        }
        return inverse;
    }

    @NotNull
    public M absolute() {
        return copy().map(Math::abs);
    }

    @NotNull
    public M negative(int x, int y) {
        return copy().negate(x, y);
    }

    @NotNull
    public M negative() {
        return copy().negate();
    }

    @NotNull
    public M with(int x, int y, double value) {
        return copy().set(x, y, value);
    }

    @NotNull
    public M withRow(int y, @NotNull double... values) {
        return copy().setRow(y, values);
    }

    @NotNull
    public M withRow(int y, @NotNull AbstractVector<?> vector) {
        return copy().setRow(y, vector);
    }

    @NotNull
    public M withColumn(int x, @NotNull double... values) {
        return copy().setColumn(x, values);
    }

    @NotNull
    public M withColumn(int x, @NotNull AbstractVector<?> vector) {
        return copy().setColumn(x, vector);
    }

    @NotNull
    public M plus(int x, int y, double value) {
        return copy().add(x, y, value);
    }

    @NotNull
    public M plusRow(int y, @NotNull double... values) {
        return copy().addRow(y, values);
    }

    @NotNull
    public M plusRow(int y, @NotNull AbstractVector<?> vector) {
        return copy().addRow(y, vector);
    }

    @NotNull
    public M plusColumn(int x, @NotNull double... values) {
        return copy().addColumn(x, values);
    }

    @NotNull
    public M plusColumn(int x, @NotNull AbstractVector<?> vector) {
        return copy().addColumn(x, vector);
    }

    @NotNull
    public M plus(@NotNull double[]... values) {
        return copy().add(values);
    }

    @NotNull
    public M plus(@NotNull AbstractMatrix<?> matrix) {
        return copy().add(matrix);
    }

    @NotNull
    public M minus(int x, int y, double value) {
        return copy().subtract(x, y, value);
    }

    @NotNull
    public M minusRow(int y, @NotNull double... values) {
        return copy().subtractRow(y, values);
    }

    @NotNull
    public M minusRow(int y, @NotNull AbstractVector<?> vector) {
        return copy().subtractRow(y, vector);
    }

    @NotNull
    public M minusColumn(int x, @NotNull double... values) {
        return copy().subtractColumn(x, values);
    }

    @NotNull
    public M minusColumn(int x, @NotNull AbstractVector<?> vector) {
        return copy().subtractColumn(x, vector);
    }

    @NotNull
    public M minus(@NotNull double[]... values) {
        return copy().subtract(values);
    }

    @NotNull
    public M minus(@NotNull AbstractMatrix<?> matrix) {
        return copy().subtract(matrix);
    }

    @NotNull
    public M times(int x, int y, double value) {
        return copy().multiply(x, y, value);
    }

    @NotNull
    public M times(double factor) {
        return copy().multiply(factor);
    }

    @NotNull
    public M timesRow(int y, double factor) {
        return copy().multiplyRow(y, factor);
    }

    @NotNull
    public M timesColumn(int x, double factor) {
        return copy().multiplyColumn(x, factor);
    }

    @NotNull
    public M times(@NotNull double[]... values) {
        return copy().multiply(values);
    }

    @NotNull
    public <V extends AbstractVector<V>> V times(@NotNull V vector) {
        checkWidth(vector);
        V product = vector.create();
        for (int y = 0; y < getHeight(); y++) {
            product.set(y, getRow(y).dot(vector));
        }
        return product;
    }

    @NotNull
    public M times(@NotNull AbstractMatrix<?> matrix) {
        matrix.checkHeight(getWidth());
        M product = create(matrix.getWidth(), getHeight());
        for (int x = 0; x < matrix.getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double value = 0;
                for (int i = 0; i < getWidth(); i++) {
                    value += get(i, y) * matrix.get(x, i);
                }
                product.set(x, y, value);
            }
        }
        return product;
    }

    @NotNull
    public M dividedBy(int x, int y, double value) {
        return copy().divide(x, y, value);
    }

    @NotNull
    public M dividedBy(double factor) {
        return copy().divide(factor);
    }

    @NotNull
    public M dividedRowBy(int y, double factor) {
        return copy().divideRow(y, factor);
    }

    @NotNull
    public M dividedColumnBy(int x, double factor) {
        return copy().divideColumn(x, factor);
    }

    @NotNull
    public M dividedBy(@NotNull double[]... values) {
        return copy().divide(values);
    }

    @NotNull
    public M normalized() {
        return copy().normalize();
    }

    @NotNull
    public M squared() {
        return times(this);
    }

    @NotNull
    public M toPow(int n) {
        checkHeight(getWidth());
        M result = createOne();
        M squared = copy();
        while (n > 0) {
            if (n % 2 == 0) {
                squared.square();
                n /= 2;
            }
            else {
                result.multiply(squared);
                n--;
            }
        }
        return result;
    }

    @NotNull
    public M transposed() {
        M transpose = create(getHeight(), getWidth());
        for (int i = 0; i < getWidth(); i++) {
            transpose.setRow(i, getColumn(i));
        }
        return transpose;
    }

    @NotNull
    public Pair<M, M> decomposeLU() {
        checkHeight(getWidth());
        M l = createOne();
        M u = copy();
        int n = getWidth();
        for (int column = 0; column < n - 1; column++) {
            for (int row = column + 1; row < n; row++) {
                double value = u.get(column, row) / u.get(column, column);
                l.set(column, row, value);
                u.set(column, row, 0);
                for (int x = column + 1; x < n; x++) {
                    u.subtract(x, row, value * u.get(x, column));
                }
            }
        }
        return new ImmutablePair<>(l, u);
    }

    @NotNull
    public M inverse() {
        return copy().invert();
    }

    public double norm() {
        return Math.sqrt(normSquared());
    }

    public double normSquared() {
        return stream().reduce(0, (identity, value) -> identity + Math.pow(value, 2));
    }

    @NotNull
    public M create() {
        return create(getWidth(), getHeight());
    }

    @NotNull
    public M createOne() {
        return createOne(getWidth(), getHeight());
    }

    @NotNull
    public M createOne(int dimension) {
        return createOne(dimension, dimension);
    }

    @NotNull
    public M createOne(int width, int height) {
        M one = create(width, height);
        for (int i = 0; i < width && i < height; i++) {
            one.set(i, i, 1);
        }
        return one;
    }

    @NotNull
    public M map(@NotNull DoubleUnaryOperator operation) {
        return map((x, y, value) -> operation.applyAsDouble(value));
    }

    @NotNull
    public M mapRow(int y, @NotNull DoubleUnaryOperator operation) {
        return mapRow(y, (x, i, value) -> operation.applyAsDouble(value));
    }

    @NotNull
    public M mapColumn(int x, @NotNull DoubleUnaryOperator operation) {
        return mapColumn(x, (i, y, value) -> operation.applyAsDouble(value));
    }

    @NotNull
    public M mapRow(int y, @NotNull Operation operation) {
        for (int x = 0; x < getWidth(); x++) {
            values[x][y] = operation.apply(x, y, get(x, y));
        }
        return getThis();
    }

    @NotNull
    public M mapColumn(int x, @NotNull Operation operation) {
        for (int y = 0; y < getHeight(); y++) {
            values[x][y] = operation.apply(x, y, get(x, y));
        }
        return getThis();
    }

    @NotNull
    public M map(@NotNull Operation operation) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                values[x][y] = operation.apply(x, y, get(x, y));
            }
        }
        return getThis();
    }

    public void forEachRow(@NotNull Consumer<? super Vector> operation) {
        rowStream().forEach(operation);
    }

    public void forEachColumn(@NotNull Consumer<? super Vector> operation) {
        columnStream().forEach(operation);
    }

    public void forEach(@NotNull DoubleConsumer operation) {
        stream().forEach(operation);
    }

    @NotNull
    public Stream<Vector> rowStream() {
        Stream.Builder<Vector> builder = Stream.builder();
        IntStream.range(0, getHeight()).mapToObj(this::getRow).forEach(builder::add);
        return builder.build();
    }

    @NotNull
    public Stream<Vector> columnStream() {
        Stream.Builder<Vector> builder = Stream.builder();
        IntStream.range(0, getWidth()).mapToObj(this::getColumn).forEach(builder::add);
        return builder.build();
    }

    @NotNull
    public DoubleStream rowStream(int y) {
        return getRow(y).stream();
    }

    @NotNull
    public DoubleStream columnStream(int x) {
        return DoubleStream.of(values[x]);
    }

    @NotNull
    public DoubleStream stream() {
        return IntStream.range(0, getWidth()).mapToObj(this::columnStream).reduce(DoubleStream.empty(), DoubleStream::concat);
    }

    @NotNull
    public static int[] check(@NotNull double[][] values) {
        int width = values.length;
        if (width == 0) {
            return new int[]{0, 0};
        }
        int height = values[0].length;
        for (int i = 1; i < width; i++) {
            if (values[i].length != height) {
                throw new IllegalArgumentException("Column " + i + " has wrong size: " + values[i].length);
            }
        }
        return new int[]{height == 0 ? 0 : width, height};
    }

    @NotNull
    public static double[][] checkWidth(int width, @NotNull double[][] values) {
        int[] size = check(values);
        if (size[0] != width) {
            throw new IllegalArgumentException("Values have wrong width: " + values.length);
        }
        return values;
    }

    @NotNull
    public static double[][] checkHeight(int height, @NotNull double[][] values) {
        int[] size = check(values);
        if (size[1] != height) {
            throw new IllegalArgumentException("Values have wrong height: " + size[1]);
        }
        return values;
    }

    @NotNull
    public static double[][] checkDimension(int width, int height, @NotNull double[][] values) {
        int[] size = check(values);
        if (size[0] != width || size[1] != height) {
            throw new IllegalArgumentException("Values have wrong dimension: " + Arrays.toString(size));
        }
        return values;
    }

    @NotNull
    public M checkWidth(@NotNull double[][] values) {
        checkWidth(getWidth(), values);
        return getThis();
    }

    @NotNull
    public M checkHeight(@NotNull double[][] values) {
        checkHeight(getHeight(), values);
        return getThis();
    }

    @NotNull
    public M checkDimension(@NotNull double[][] values) {
        checkDimension(getWidth(), getHeight(), values);
        return getThis();
    }

    @NotNull
    public M checkWidth(@NotNull AbstractVector<?> vector) {
        vector.checkDimension(getWidth());
        return getThis();
    }

    @NotNull
    public M checkHeight(@NotNull AbstractVector<?> vector) {
        vector.checkDimension(getHeight());
        return getThis();
    }

    @NotNull
    public M checkWidth(@NotNull double[] values) {
        AbstractVector.checkDimension(getWidth(), values);
        return getThis();
    }

    @NotNull
    public M checkHeight(@NotNull double[] values) {
        AbstractVector.checkDimension(getHeight(), values);
        return getThis();
    }

    @NotNull
    public M checkWidth(int width) {
        if (getWidth() != width) {
            throw new IllegalArgumentException("Matrix has wrong width: " + getWidth());
        }
        return getThis();
    }

    @NotNull
    public M checkHeight(int height) {
        if (getHeight() != height) {
            throw new IllegalArgumentException("Matrix has wrong height: " + getHeight());
        }
        return getThis();
    }

    @NotNull
    public M checkDimension(int width, int height) {
        return checkWidth(width).checkHeight(height);
    }

    @NotNull
    public M checkDimension(@NotNull AbstractMatrix<?> matrix) {
        return checkDimension(matrix.getWidth(), matrix.getHeight());
    }

    public boolean equals(int x, int y, double value) {
        return get(x, y) == value;
    }

    public boolean equalsRow(int y, @NotNull double... values) {
        checkWidth(values);
        for (int x = 0; x < getWidth(); x++) {
            if (!equals(x, y, values[x])) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsRow(int y, @NotNull AbstractVector<?> vector) {
        checkWidth(vector);
        return getRow(y).equals(vector);
    }

    public boolean equalsColumn(int x, @NotNull double... values) {
        checkHeight(values);
        return Arrays.equals(this.values[x], values);
    }

    public boolean equalsColumn(int x, @NotNull AbstractVector<?> vector) {
        checkHeight(vector);
        return getColumn(x).equals(vector);
    }

    public boolean equals(@NotNull double[]... values) {
        checkDimension(values);
        for (int x = 0; x < getWidth(); x++) {
            if (!equalsColumn(x, values[x])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(@NotNull AbstractMatrix<?> matrix, double error) {
        return minus(matrix).norm() < error;
    }

    public boolean equals(@NotNull AbstractMatrix<?> matrix) {
        return equals(matrix.values);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractMatrix<?> && equals((AbstractMatrix<?>) obj);
    }

    @Override
    public int hashCode() {
        int[] hashCodes = new int[getWidth()];
        for (int x = 0; x < getWidth(); x++) {
            hashCodes[x] = Arrays.hashCode(values[x]);
        }
        return Arrays.hashCode(hashCodes);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(getRows()).map(Object::toString).toArray());
    }

    @NotNull
    public String display() {
        List<StringBuilder> rows = new ArrayList<>(getHeight());
        for (int y = 0; y < getHeight(); y++) {
            rows.add(new StringBuilder("["));
        }
        for (int x = 0; x < getWidth(); x++) {
            String[] values = getColumnDisplays(x);
            for (int y = 0; y < getHeight(); y++) {
                StringBuilder row = rows.get(y);
                if (x > 0) {
                    row.append(", ");
                }
                row.append(values[y]);
            }
        }
        rows.forEach(row -> row.append("]"));
        return rows.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    @NotNull
    private String[] getColumnDisplays(int x) {
        String[] displays = new String[getHeight()];
        int maxLength = 0;
        for (int y = 0; y < getHeight(); y++) {
            String value = String.valueOf(get(x, y));
            displays[y] = value;
            maxLength = Math.max(maxLength, value.length());
        }
        for (int y = 0; y < getHeight(); y++) {
            displays[y] = StringUtil.pad(displays[y], " ", maxLength);
        }
        return displays;
    }

    @NotNull
    public abstract M copy();

    @NotNull
    public abstract M create(int width, int height);

    @NotNull
    public abstract M create(@NotNull double[]... values);

    @NotNull
    protected abstract M getThis();

    @FunctionalInterface
    public interface Operation {
        double apply(int x, int y, double value);
    }
}
