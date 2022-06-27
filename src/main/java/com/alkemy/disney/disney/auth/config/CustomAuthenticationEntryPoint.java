package com.alkemy.disney.disney.auth.config;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        JsonGenerator jsonGenerator = Json.createGenerator(response.getWriter());
        jsonGenerator.writeStartObject()
                .write("message", "Access Denied")
                .write("details", "A valid token is required")
                .write("status", "FORBIDDEN")
                .write("timestamp", new Date().toString())
                .writeEnd()
                .close();

    }
}
