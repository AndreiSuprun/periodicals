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

/**
 * Class for picture file upload processing
 *
 * @author Andrei Suprun
 */
public class PictureUploader {

    private static final String PART_NAME = "picture";
    private static final String SEMICOLON = ";";
    private static final String FILE_NAME = "filename";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String ESCAPED_QUOTATION = "\"";
    private static final String EMPTY_STRING = "";
    private static final char EQUAL_SIGN = '=';
    private static final char SLASH_SIGN = '/';
    private static final char DOT = '.';

    /**
     * Receiving input stream from HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return InputStream input stream of file
     * @throws IOException
     * @throws ServletException
     */
    public static InputStream receiveInputStream(HttpServletRequest request) throws IOException, ServletException {
        Part part = request.getPart(PART_NAME);
        return part.getInputStream();
    }

    /**
     * Generate file name using UUID generator
     *
     * @param fileExtension extension of uploaded file
     * @return generated file name
     * @throws IOException
     */
    public static String receiveFileName(String fileExtension) throws IOException {
        if (fileExtension == null) {
            return null;
        }
        String uuidFileName = UUID.randomUUID().toString();
        String   fileName = uuidFileName + fileExtension;
        return fileName;
    }

    /**
     * Receiving file extension from HttpServletRequest
     *
     * @param request HttpServletRequest
     * @return extension of the uploaded file
     * @throws IOException
     * @throws ServletException
     */
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
