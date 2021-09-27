package com.galua.uniquecounter.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Util {

    public static String readURLToString(String url) throws IOException {
        try (InputStream inputStream = new URL(url).openStream()) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
