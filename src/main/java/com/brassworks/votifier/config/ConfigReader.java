package com.brassworks.votifier.config;

import com.brassworks.votifier.utils.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);

    public static String HOST;
    public static int PORT;
    public static boolean DEBUG;
    public static String SITE1;
    public static String SITE2;
    public static String SITE3;

    private ConfigReader() {
    }

    public static void readFile(Path path) throws IOException {
        String config = readOrCopyFile(path.resolve("votifier.json"), "/votifier.json");
        JSONObject configObject = new JSONObject(config);
        HOST = configObject.getString("host");
        PORT = Integer.parseInt(configObject.getString("port"));
        DEBUG = configObject.getBoolean("debug");
        SITE1 = configObject.getString("site1");
        SITE2 = configObject.getString("site2");
        SITE3 = configObject.getString("site3");
    }

    public static String readOrCopyFile(Path path, String exampleFile) throws IOException {
        File file = path.toFile();
        if (!file.exists()) {
            LOGGER.info("File does not exist, attempting to copy from resources: " + exampleFile);
            InputStream stream = ConfigReader.class.getResourceAsStream(exampleFile);
            if (stream == null) {
                LOGGER.error("Cannot load example file: " + exampleFile);
                throw new NullPointerException("Cannot load example file");
            }

            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            Files.copy(stream, path);
            LOGGER.info("File copied to: " + path.toString());
        } else {
            LOGGER.info("File already exists: " + path.toString());
        }
        return Files.readString(path);
    }
}