package ru.itmo.web.lesson4.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Scanner;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        Scanner sc = new Scanner(uri).useDelimiter("\\+");
        while (sc.hasNext()) {
            String currentFilePath = sc.next();
            File file = new File(getServletContext().getRealPath("."),
                    "../../src/main/webapp/" + currentFilePath);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath(currentFilePath));
            }
            if (file.isFile()) {
                if (response.getContentType() == null) {
                    response.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
                }
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}

