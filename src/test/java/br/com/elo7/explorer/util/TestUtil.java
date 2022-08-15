package br.com.elo7.explorer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class TestUtil {
    private static final String TEST_FILE_PATH = "src/test/resources/__files/";

    private TestUtil() {
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

    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        try {
            return (T) new ObjectMapper()
                    .readValue(
                            jsonString,
                            clazz
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String StringFromJsonFile(String jsonFile,  Class<T> clazz){
        try {
            var obj = fromJsonFile(jsonFile, clazz);
            return  new ObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}