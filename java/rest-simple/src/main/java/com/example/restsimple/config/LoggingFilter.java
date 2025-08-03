package com.example.restsimple.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_KEY = "correlationId";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String correlationId = getOrGenerateCorrelationId(request);
        
        try {
            MDC.put(CORRELATION_ID_KEY, correlationId);
            response.setHeader(CORRELATION_ID_HEADER, correlationId);
            
            long startTime = System.currentTimeMillis();
            logger.debug("Request started: {} {} - Correlation ID: {}", 
                        request.getMethod(), request.getRequestURI(), correlationId);
            
            filterChain.doFilter(request, response);
            
            long duration = System.currentTimeMillis() - startTime;
            
            // Log basic timing info for all requests
            if (response.getStatus() >= 400) {
                logger.warn("Request failed: {} {} - Status: {} - Duration: {}ms - Correlation ID: {}", 
                           request.getMethod(), request.getRequestURI(), response.getStatus(), 
                           duration, correlationId);
            } else {
                logger.debug("Request completed: {} {} - Status: {} - Duration: {}ms - Correlation ID: {}", 
                            request.getMethod(), request.getRequestURI(), response.getStatus(), 
                            duration, correlationId);
            }
                       
        } finally {
            MDC.clear();
        }
    }
    
    private String getOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }
}