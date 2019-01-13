package org.deepercreeper.common.util;

import org.deepercreeper.common.interfaces.*;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.*;

public class Util {
    private Util() {}

    public static void sleep(long millis, int nanos) {
        tryQuietly(() -> Thread.sleep(millis, nanos));
    }

    public static void sleep(long millis) {
        tryQuietly(() -> Thread.sleep(millis));
    }

    public static void await(@NotNull CountDownLatch latch) {
        tryQuietly((Exceptionable) latch::await);
    }

    @NotNull
    public static Class<?> getClass(@NotNull String name) {
        try {
            return Class.forName(name);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not find class: " + name);
        }
    }

    @NotNull
    public static <T> Class<? extends T> getClass(@NotNull String name, @NotNull Class<T> superClass) {
        Class<?> classLiteral = getClass(name);
        if (superClass.isAssignableFrom(classLiteral)) {
            //noinspection unchecked
            return (Class<? extends T>) classLiteral;
        }
        throw new IllegalArgumentException("Class is not of type " + superClass + ": " + name);
    }

    @NotNull
    public static int[] toArray(@NotNull Collection<Integer> integers) {
        int[] array = new int[integers.size()];
        int index = 0;
        for (int integer : integers) {
            array[index++] = integer;
        }
        return array;
    }

    @NotNull
    public static List<Integer> toList(@NotNull int[] integers) {
        List<Integer> list = new ArrayList<>(integers.length);
        for (int integer : integers) {
            list.add(integer);
        }
        return list;
    }

    @NotNull
    public static Set<Integer> toSet(@NotNull int[] integers) {
        Set<Integer> set = new HashSet<>();
        for (int integer : integers) {
            set.add(integer);
        }
        return set;
    }

    public static void openURL(@NotNull URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            }
            catch (Exception e) {
                throw new RuntimeException("Could not browse URL: " + uri.toString(), e);
            }
        }
    }

    @NotNull
    public static Document readDocument(@NotNull String document) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not parse document: " + document, e);
        }
    }

    @NotNull
    public static <T, U> BiConsumer<T, U> silent(@NotNull ExBiConsumer<T, U> operation) {
        return (t, u) -> doTry(() -> operation.accept(t, u));
    }

    @NotNull
    public static <T, U, R> BiFunction<T, U, R> silent(@NotNull ExBiFunction<T, U, R> operation) {
        return (t, u) -> doTry(() -> operation.apply(t, u));
    }

    @NotNull
    public static <T, U> BiPredicate<T, U> silent(@NotNull ExBiPredicate<T, U> operation) {
        return (t, u) -> doTry(() -> operation.test(t, u));
    }

    @NotNull
    public static Runnable silent(@NotNull Exceptionable operation) {
        return () -> doTry(operation);
    }

    @NotNull
    public static <T> Consumer<T> silent(@NotNull ExConsumer<T> operation) {
        return t -> doTry(() -> operation.accept(t));
    }

    @NotNull
    public static <T, R> Function<T, R> silent(@NotNull ExFunction<T, R> operation) {
        return t -> doTry(() -> operation.apply(t));
    }

    @NotNull
    public static <T> Predicate<T> silent(@NotNull ExPredicate<T> operation) {
        return t -> doTry(() -> operation.test(t));
    }

    @NotNull
    public static <T> Supplier<T> silent(@NotNull Callable<T> operation) {
        return () -> doTry(operation);
    }

    public static void doTry(@NotNull Exceptionable operation) {
        doTry(() -> {
            operation.run();
            return null;
        });
    }

    public static <T> T doTry(@NotNull Callable<T> operation) {
        try {
            return operation.call();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to do operation:", e);
        }
    }

    public static void tryQuietly(@NotNull Exceptionable operation) {
        tryQuietly(() -> {
            operation.run();
            return null;
        });
    }

    @NotNull
    public static <T> Optional<T> tryQuietly(@NotNull Callable<T> operation) {
        try {
            return Optional.ofNullable(operation.call());
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public static double optimize(@NotNull Function<Double, Double> function, double leftKey, double rightKey, double precision) {
        return optimize(function, leftKey, rightKey, precision, (left, right) -> (left + right) / 2, (left, right) -> left - right);
    }

    @NotNull
    public static <T extends Comparable<T>, R extends Comparable<R>> T optimize(@NotNull Function<T, R> function, @NotNull T leftKey, @NotNull T rightKey, @NotNull T precision,
                                                                                @NotNull BiFunction<T, T, T> split, @NotNull BiFunction<T, T, T> difference) {
        T midKey = split.apply(leftKey, rightKey);
        R midValue = function.apply(midKey);

        T leftMidKey, rightMidKey;
        R leftMidValue, rightMidValue;

        do {
            leftMidKey = split.apply(leftKey, midKey);
            rightMidKey = split.apply(midKey, rightKey);
            leftMidValue = function.apply(leftMidKey);
            rightMidValue = function.apply(rightMidKey);
            if (leftMidValue.compareTo(midValue) > 0) {
                rightKey = midKey;

                midKey = leftMidKey;
                midValue = leftMidValue;
            }
            else if (rightMidValue.compareTo(midValue) > 0) {
                leftKey = midKey;

                midKey = rightMidKey;
                midValue = rightMidValue;
            }
            else {
                leftKey = leftMidKey;

                rightKey = rightMidKey;
            }
        } while (difference.apply(rightKey, leftKey).compareTo(precision) > 0);
        return midKey;
    }
}
