package web.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static final String EXTENSION_DELIMITER = ".";

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(EXTENSION_DELIMITER));
    }

    public static boolean hasComma(String filename) {
        return filename.contains(EXTENSION_DELIMITER);
    }

    public static byte[] loadFileFrom(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(FileUtils.class.getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(path);
    }
}
