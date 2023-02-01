package com.telesoftas.cityget.util;

import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

public class TestUtils {
    @SneakyThrows
    public static String getJsonFromResource(String path) {
        return Files.readString(ResourceUtils.getFile("classpath:" + path).toPath());
    }

    public static String buildUpdateJson(String name, String photo) {
        return String.format("""
                {"name": "%s", "photo": "%s"}
                """, name, photo);
    }
}
