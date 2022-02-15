package ru.itmo.wp.util;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriteUtil {
    public static void writeResponse(String output, ServletResponse response) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.print(output);
        }
    }

}
