package ru.itis.hauntedo.simbirtest.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.AuthenticationHeaderException;
import ru.itis.hauntedo.simbirtest.service.jwt.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class TokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, ObjectMapper objectMapper) {
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            String token = getAuthorizationToken(authorizationHeader);

            if (token != null) {
                UserResponse userResponse = jwtTokenService.getUserByToken(token);
                PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(userResponse, token);

                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else if (!SecurityContextHolder.getContext().getAuthentication().getCredentials().equals(token)) {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            objectMapper.writeValue(response.getWriter(), Collections.singletonMap("Error", e.getMessage()));
        }
    }

    private String getAuthorizationToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new AuthenticationHeaderException("Authorization header token is wrong");
        }

        String token = StringUtils.removeStart(authorizationHeader, "Bearer".trim());

        if (token == null) {
            throw new AuthenticationHeaderException("Authorization header token is empty");
        }
        return token;
    }
}
