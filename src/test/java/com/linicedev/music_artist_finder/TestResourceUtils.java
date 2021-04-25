package com.linicedev.music_artist_finder;

import static java.nio.file.Files.readAllBytes;
import static org.springframework.util.ResourceUtils.getFile;

import java.io.File;

public final class TestResourceUtils {

    private TestResourceUtils() {
    }

    public static String readFile(String filePath) throws Exception {
        File file = getFile("classpath:" + filePath);
        return new String(readAllBytes(file.toPath()));
    }

}
