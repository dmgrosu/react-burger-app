package com.example.reactburgerapp.config;

import com.example.reactburgerapp.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JWTFilter extends GenericFilterBean {

    private TokenService tokenService;

    public JWTFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String token = getTokenFromRequest(request);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.sendError(HttpServletResponse.SC_OK, "success");
            return;
        }

        if (token == null) {
            filterChain.doFilter(req, res);
            return;
        }

        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String userEmail = userData.get("email");
        String userId = userData.get("id");

        if (userEmail == null || userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {

            request.setAttribute("userId", userId);
            request.setAttribute("username", userEmail);

            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(req, res);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
