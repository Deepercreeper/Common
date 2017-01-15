package org.deepercreeper.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.net.*;
import java.util.*;

public class IOUtil
{
    private static final Set<String> IGNORED_MESSAGES = new HashSet<>(Arrays.asList("connection reset", "socket closed"));

    private IOUtil() {}

    public static String getMacAddress()
    {
        try
        {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces)
            {
                if (networkInterface.getName().equalsIgnoreCase("wlan0"))
                {
                    return buildMacAddress(networkInterface.getHardwareAddress());
                }
            }
        }
        catch (Exception ignored)
        {
        }
        return "02:00:00:00:00:00";
    }

    private static String buildMacAddress(byte[] macAddressBytes)
    {
        if (macAddressBytes == null)
        {
            throw new RuntimeException("Could not get MAC address");
        }
        List<String> hexStrings = new ArrayList<>(macAddressBytes.length);
        for (byte b : macAddressBytes)
        {
            hexStrings.add(Integer.toHexString(b & 0xff));
        }
        return StringUtil.join(hexStrings, ":");
    }

    public static String readSocketMessage(BufferedReader input)
    {
        try
        {
            return input.readLine();
        }
        catch (SocketTimeoutException ignored)
        {
        }
        catch (SocketException e)
        {
            if (!IGNORED_MESSAGES.contains(e.getMessage().toLowerCase()))
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAddress()
    {
        try
        {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            return in.readLine();
        }
        catch (Exception e)
        {
            return "<Unknown address>";
        }
    }

    public static String getLocalAddress()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            return "<Unknown address>";
        }
    }

    public static PrintWriter createWriter(File file)
    {
        try
        {
            return new PrintWriter(file);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("Could not create writer:", e);
        }
    }

    public static LineIterator createLineIterator(File file)
    {
        try
        {
            return FileUtils.lineIterator(file, "UTF-8");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not open line iterator:", e);
        }
    }
}
