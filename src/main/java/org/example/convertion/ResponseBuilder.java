package org.example.convertion;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseBuilder {
    public void build(HttpServletResponse resp, String response, int status) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (!response.isEmpty()) {
            resp.getWriter().write(response);
        }
        resp.setStatus(status);
    }
}
