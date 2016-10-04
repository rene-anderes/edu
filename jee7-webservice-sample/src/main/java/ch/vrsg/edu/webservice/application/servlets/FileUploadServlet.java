package ch.vrsg.edu.webservice.application.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import static java.util.stream.Collectors.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/FileUploadServlet"})
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB
public class FileUploadServlet extends HttpServlet {
 
    private static final long serialVersionUID = -7675015835808808921L;
 
    @Inject
    private Logger logger;
    
    private static final Path UPLOADFILEPATH = Paths.get(System.getProperty("user.home"), "uploads");
     
    @Override
    public void init() throws ServletException {
        super.init();
        // creates the save directory if it does not exists
        final File fileSaveDir = UPLOADFILEPATH.toFile();
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        logger.info("Upload File Directory= " + UPLOADFILEPATH);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HashSet<String> namesOfAllFiles = new HashSet<>(request.getParts().size());
        for (Part part : request.getParts()) {
            final Optional<String> fileName = getFileName(part);
            if (fileName.isPresent()) {
                handlePart(part, Paths.get(UPLOADFILEPATH.toString(), fileName.get()));
                namesOfAllFiles.add(fileName.get());
            }
        }
        request.setAttribute("message", "File(s) uploaded successfully! File(s): " + namesOfAllFiles.stream().collect(joining(";")));
        getServletContext().getRequestDispatcher("/uploadResponse.jsp").forward(request, response);
    }

    private void handlePart(Part part, final Path fileName) throws IOException, FileNotFoundException {
        try (InputStream fileInputStream = part.getInputStream()) {
            try (FileOutputStream out = new FileOutputStream(fileName.toFile())) {
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = fileInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            }
        }
    }
    
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private Optional<String> getFileName(final Part part) {
        final String contentDisp = part.getHeader("content-disposition");
        logger.debug("content-disposition header= '" + contentDisp + "'");
        return Arrays.stream(contentDisp.split(";"))
                        .filter(token -> token.trim().startsWith("filename"))
                        .map(token -> token.substring(token.indexOf("=") + 2, token.length()-1))
                        .findFirst();
    }
}