package com.example.bookstorebackend.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AdminOnlyInterceptor implements HandlerInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull Object handler) throws Exception {

        List<String> roles = (List<String>) req.getAttribute("roles");

        if (roles == null || !roles.contains("ROLE_ADMIN")) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("{\"isSuccess\":false, \"message\":\"FORBIDDEN\"}");
            return false;
        }
        return true;
    }
}
