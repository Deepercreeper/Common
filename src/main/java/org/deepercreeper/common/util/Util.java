package org.deepercreeper.common.util;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Util
{
    private Util() {}

    public static void sleep(long timeout)
    {
        try
        {
            Thread.sleep(timeout);
        }
        catch (InterruptedException ignored)
        {
        }
    }

    public static void await(CountDownLatch latch)
    {
        try
        {
            latch.await();
        }
        catch (InterruptedException ignored) {}
    }

    public static Class<?> getClass(String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Could not find class: " + name);
        }
    }

    public static <T> Class<? extends T> getClass(String name, Class<T> superClass)
    {
        Class<?> classLiteral = getClass(name);
        if (superClass.isAssignableFrom(classLiteral))
        {
            //noinspection unchecked
            return (Class<? extends T>) classLiteral;
        }
        throw new IllegalArgumentException("Class is not of type " + superClass + ": " + name);
    }

    public static int[] toArray(Collection<Integer> integers)
    {
        int[] array = new int[integers.size()];
        int index = 0;
        for (int integer : integers)
        {
            array[index++] = integer;
        }
        return array;
    }

    public static List<Integer> toList(int[] integers)
    {
        List<Integer> list = new ArrayList<>(integers.length);
        for (int integer : integers)
        {
            list.add(integer);
        }
        return list;
    }

    public static Set<Integer> toSet(int[] integers)
    {
        Set<Integer> set = new HashSet<>();
        for (int integer : integers)
        {
            set.add(integer);
        }
        return set;
    }

    public static void openURL(URI uri)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                desktop.browse(uri);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Could not browse URL: " + uri.toString());
            }
        }
    }

    public static Document readDocument(String document)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not parse document: " + document, e);
        }
    }
}
