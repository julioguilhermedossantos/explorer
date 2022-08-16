package br.com.elo7.explorer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class TestUtil {
    private static final String TEST_FILE_PATH = "src/test/resources/__files/";
    private static final String CLASSPATH_RESOURCE = "__files/";

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
            return  new ObjectMapper().writeValueAsString(fromJsonFile(jsonFile, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String StringFromJsonFile(String jsonFile){
        try {
            return new String(Files.readAllBytes(new ClassPathResource(CLASSPATH_RESOURCE.concat(jsonFile)).getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}