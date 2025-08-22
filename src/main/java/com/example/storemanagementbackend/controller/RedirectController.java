package com.example.storemanagementbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {

    @RequestMapping("/**")
    public void redirectToHttps(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null) {
            scheme = request.getScheme();
        }
        
        // Only redirect if not already HTTPS and not a REST API call
        if (!"https".equals(scheme) && !request.getRequestURI().startsWith("/api/")) {
            String httpsUrl = "https://" + request.getServerName() + request.getRequestURI();
            if (request.getQueryString() != null) {
                httpsUrl += "?" + request.getQueryString();
            }
            response.sendRedirect(httpsUrl);
        }
    }
}