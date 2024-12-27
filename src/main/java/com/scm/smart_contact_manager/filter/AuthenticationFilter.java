package com.scm.smart_contact_manager.filter;

import com.scm.smart_contact_manager.entity.request.AuthenticationRequest;
import com.scm.smart_contact_manager.entity.response.AuthenticationResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j

public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${authentication.url}")
    private String authenticationURL;

    private final RestTemplate restTemplate;

    public AuthenticationFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        log.info("Request: {}, contextPath: {} URI: {}", request.getPathInfo(), request.getContextPath(), request.getRequestURI());

        if(Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            final String token = authHeader.substring(7);

            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setToken(token);

            ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.postForEntity(UriComponentsBuilder.fromUriString(authenticationURL)
                    .build()
                    .toUri(), authenticationRequest, AuthenticationResponse.class);


            if(!HttpStatus.OK.equals(responseEntity.getStatusCode()) && !Objects.requireNonNull(responseEntity.getBody()).isAuthenticated()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e){
            log.error("AuthenticationFilter: doFilterInternal:: Got Exception: ", e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
