package com.suprun.periodicals.util;

import com.suprun.periodicals.entity.Periodical;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Class for processing file uploading.
 *
 * @author Andrei Suprun
 */
public class PictureService {

    private static String UPLOAD_DIRECTORY = Resource.FILE_UPLOAD.getProperty("upload.path");
    private static final String SLASH = "/";

    /**
     * Upload file to local storage
     *
     * @param inputStream file input stream
     * @param fileName path to file
     * @throws IOException
     */
    public static void uploadFile(InputStream inputStream, String fileName) throws IOException {
        if (fileName != null) {
            Path uploadDir = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadDir)) {
                Files.createDirectory(uploadDir);
            }
            Files.copy(inputStream, Paths.get(UPLOAD_DIRECTORY + SLASH + fileName));
        }
    }
}
