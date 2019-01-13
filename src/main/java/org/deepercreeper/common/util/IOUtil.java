package org.deepercreeper.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.*;

public class IOUtil {
    private static final Set<String> IGNORED_MESSAGES = new HashSet<>(Arrays.asList("connection reset", "socket closed"));

    private IOUtil() {}

    @NotNull
    public static Optional<String> getMacAddress() {
        return Util.tryQuietly(NetworkInterface::getNetworkInterfaces)
                .flatMap(interfaces -> Collections.list(interfaces).stream().filter(networkInterface -> networkInterface.getName().equalsIgnoreCase("wlan0")).findFirst()
                        .map(Util.silent(NetworkInterface::getHardwareAddress)).map(IOUtil::buildMacAddress));
    }

    @NotNull
    private static String buildMacAddress(@NotNull byte[] macAddressBytes) {
        List<String> hexStrings = new ArrayList<>(macAddressBytes.length);
        for (byte b : macAddressBytes) {
            hexStrings.add(Integer.toHexString(b & 0xff));
        }
        return StringUtil.join(hexStrings, ":");
    }

    @NotNull
    public static Optional<String> readSocketMessage(@NotNull BufferedReader input) {
        try {
            return Optional.ofNullable(input.readLine());
        }
        catch (SocketTimeoutException ignored) {}
        catch (SocketException e) {
            if (!IGNORED_MESSAGES.contains(e.getMessage().toLowerCase())) {
                throw new RuntimeException("Failed to read socket message:", e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read socket message:", e);
        }
        return Optional.empty();
    }

    @NotNull
    public static Optional<String> getAddress() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            return Optional.ofNullable(in.readLine());
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @NotNull
    public static String readSite(String address) {
        StringBuilder site = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(address).openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                site.append(line).append("\n");
            }
            IOUtils.closeQuietly(reader);
            return site.toString();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not read site: " + address, e);
        }
    }

    @NotNull
    public static Optional<String> getLocalAddress() {
        try {
            return Optional.ofNullable(InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e) {
            return Optional.empty();
        }
    }

    @NotNull
    public static PrintWriter createWriter(File file) {
        try {
            return new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Could not create writer:", e);
        }
    }

    @NotNull
    public static LineIterator createLineIterator(File file) {
        try {
            return FileUtils.lineIterator(file, "UTF-8");
        }
        catch (IOException e) {
            throw new RuntimeException("Could not open line iterator:", e);
        }
    }
}
