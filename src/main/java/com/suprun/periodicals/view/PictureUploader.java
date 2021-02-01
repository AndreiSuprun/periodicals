package com.suprun.periodicals.view;

import com.suprun.periodicals.util.Resource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class PictureUploader {

    private static final String PART_NAME = "picture";
    private static final String SEMICOLON = ";";
    private static final String FILE_NAME = "filename";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String ESCAPED_QUOTATION = "\"";
    private static final String EMPTY_STRING = "";

    private static final char EQUAL_SIGN = '=';
    private static final char SLASH_SIGN = '/';
    private static final char ESCAPED_BACK_SLASH = '\\';
    private static final char DOT = '.';
    private static final String UPLOAD_DIRECTORY = Resource.FILE_UPLOAD.getProperty("upload.path");

    public static InputStream receiveInputStream(HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart(PART_NAME);
        return part.getInputStream();
    }

    public static String receiveFileName(String fileExtension) throws IOException {
        if (fileExtension == null) {
            return null;
        }
        String uuidFileName = UUID.randomUUID().toString();
        String   fileName = uuidFileName + fileExtension;
        return fileName;
    }

    public static String getFileExtension(HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart(PART_NAME);
            for (String cd : part.getHeader(CONTENT_DISPOSITION).split(SEMICOLON)) {
                if (cd.trim().startsWith(FILE_NAME)) {
                    String filename = cd.substring(cd.indexOf(EQUAL_SIGN) + 1).trim()
                            .replace(ESCAPED_QUOTATION, EMPTY_STRING);
                    if (!filename.isEmpty()) {
                    return filename.substring(filename.lastIndexOf(SLASH_SIGN) + 1)
                            .substring(filename.lastIndexOf(DOT));
                }
            }
        }
        return null;
    }
}
