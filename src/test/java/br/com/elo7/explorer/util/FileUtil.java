package br.com.elo7.explorer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class FileUtil {
    private static final String TEST_FILE_PATH = "src/test/resources/__files/";

    private FileUtil() {
    }

    public static <T> T fromJsonFile(String jsonFile, Class<T> clazz) {
        try {
            return (T) new ObjectMapper()
                    .readValue(
                            new File(TEST_FILE_PATH.concat(jsonFile)),
                            clazz
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}