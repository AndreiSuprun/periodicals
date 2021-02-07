package com.suprun.periodicals.view.tag;

import com.suprun.periodicals.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Business logic for custom tag.
 * Sends base64 encoded picture to jsp.
 *
 * @author Andrei Suprun
 */
public class CustomImgSrcTag extends SimpleTagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomImgSrcTag.class);
    private String pictureURL;
    private static final String SLASH = "\\";
    private static final String UPLOAD_DIRECTORY = Resource.FILE_UPLOAD.getProperty("upload.path");
    private static final String LOGO_FILE = Resource.FILE_UPLOAD.getProperty("logo.file");;

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    /**
     * Writes base64 encoded picture to out stream of jspContext
     *
     * @throws IOException IOException
     */
    @Override
    public void doTag() throws IOException {
        InputStream inputStream;
        try{
            if (!pictureURL.isEmpty()) {

        String pathToFile = UPLOAD_DIRECTORY + SLASH + pictureURL;
                inputStream = new BufferedInputStream(new FileInputStream(pathToFile));}
            else {
                String pathToLogo = UPLOAD_DIRECTORY + SLASH + LOGO_FILE;
                inputStream = new BufferedInputStream(new FileInputStream(pathToLogo));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFound exception was thrown while picture periodical processing");
            URL fileLocation = this.getClass().getClassLoader().getResource(LOGO_FILE);
            Path file = null;
            if (fileLocation != null) {
                file = Paths.get(fileLocation.toString().substring(6));}
            inputStream = new BufferedInputStream(new FileInputStream(file.toString()));
        }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            inputStream.close();
            outputStream.close();
            JspWriter out = getJspContext().getOut();
            out.println("data:image/jpg;base64," + base64Image);
    }
}
