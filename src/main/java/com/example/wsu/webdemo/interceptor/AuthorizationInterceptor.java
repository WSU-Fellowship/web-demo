package com.example.wsu.webdemo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * HTTP interceptor responsible for catching unauthenticated requests (no 'role' header)
 * and creating/adding an authentication token to the security context
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Extract value from header used to establish authority
        String role = request.getHeader("role");
        if (role == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // halt and return 401 response
        }

        // Fake an authenticated user with an authority based on the HTTP header value
        // and add them to Spring's security context so the framework can resolve our
        // method-level security annotations
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            new User("Luke Skywalker", "the3F0rc3", Collections.emptyList()),
            null,
            AuthorityUtils.createAuthorityList(role.toUpperCase())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
