package org.example.convertion;

import jakarta.servlet.http.HttpServletRequest;

public class IdParser {

    public String[] parse(HttpServletRequest req) {
        return req.getRequestURI().replaceAll("[^\\d.]", " ").trim().split(" ");
    }
}
