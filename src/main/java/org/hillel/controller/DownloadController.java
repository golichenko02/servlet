package org.hillel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloadController {

    @RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {
        final String filesDir = request.getServletContext().getRealPath("/WEB-INF/files/");
        final Path file = Paths.get(filesDir, fileName);
        if (Files.exists(file)) {
            try {
                final ServletOutputStream outputStream = response.getOutputStream();
                Files.copy(file, outputStream);
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else
            throw new IllegalArgumentException("File not found: " + fileName);
    }
}
