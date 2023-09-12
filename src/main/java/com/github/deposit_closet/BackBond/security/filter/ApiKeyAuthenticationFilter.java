package com.github.deposit_closet.BackBond.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyAuthenticationFilter implements Filter {

    @Value("${spring.api.key}")
    private String centralApiKey;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String apiKey = getApiKey((HttpServletRequest) servletRequest);

        if (apiKey != null && isValidApiKey(apiKey)) {
            // authentication successful
            SecurityContextHolder.getContext().setAuthentication(createAuthenticationToken(apiKey));
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid API Key");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getApiKey(HttpServletRequest httpRequest) {
        return httpRequest.getHeader("Authorization");
    }

    private boolean isValidApiKey(String apiKey) {
        return apiKey.equals(centralApiKey);
    }

    private Authentication createAuthenticationToken(String apiKey) {
        return new UsernamePasswordAuthenticationToken(
                apiKey, null, AuthorityUtils.NO_AUTHORITIES
        );
    }
}
