package com.example.restsimple.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DetailedLoggingFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(DetailedLoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_KEY = "correlationId";
    
    private final LoggingConfiguration loggingConfig;
    
    public DetailedLoggingFilter(@Autowired(required = false) LoggingConfiguration loggingConfig) {
        this.loggingConfig = loggingConfig != null ? loggingConfig : createDefaultConfig();
    }
    
    private LoggingConfiguration createDefaultConfig() {
        LoggingConfiguration config = new LoggingConfiguration();
        config.setDetailedRequestLogging(true);
        config.setLogRequestHeaders(true);
        config.setLogResponseHeaders(false);
        config.setLogRequestBody(true);
        config.setLogResponseBody(true);
        config.setMaxBodySize(1000);
        return config;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        // Skip detailed logging if disabled or for health checks and swagger docs
        if (!loggingConfig.isDetailedRequestLogging() || shouldSkipLogging(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String correlationId = getOrGenerateCorrelationId(request);
        
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        
        try {
            MDC.put(CORRELATION_ID_KEY, correlationId);
            response.setHeader(CORRELATION_ID_HEADER, correlationId);
            
            long startTime = System.currentTimeMillis();
            
            // Log detailed request information
            logRequestDetails(requestWrapper, correlationId);
            
            filterChain.doFilter(requestWrapper, responseWrapper);
            
            long duration = System.currentTimeMillis() - startTime;
            
            // Log detailed response information
            logResponseDetails(responseWrapper, duration, correlationId);
            
            // Copy cached response content back to original response
            responseWrapper.copyBodyToResponse();
            
        } finally {
            MDC.clear();
        }
    }
    
    private boolean shouldSkipLogging(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.equals("/healthz") || 
               uri.startsWith("/swagger-ui") || 
               uri.startsWith("/v3/api-docs") ||
               uri.equals("/favicon.ico");
    }
    
    private void logRequestDetails(ContentCachingRequestWrapper request, String correlationId) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        
        logger.info("=== REQUEST START === Method: {} URI: {}{} - Correlation ID: {}", 
                   method, uri, queryString != null ? "?" + queryString : "", correlationId);
        
        // Log headers (excluding sensitive ones)
        if (loggingConfig.isLogRequestHeaders()) {
            Map<String, String> headers = getRequestHeaders(request);
            if (!headers.isEmpty()) {
                logger.debug("Request Headers: {}", headers);
            }
        }
        
        // Log request body for POST/PUT/PATCH
        if (loggingConfig.isLogRequestBody() && shouldLogRequestBody(method)) {
            String requestBody = getRequestBody(request);
            if (requestBody != null && !requestBody.trim().isEmpty()) {
                logger.info("Request Body: {}", requestBody);
            }
        }
    }
    
    private void logResponseDetails(ContentCachingResponseWrapper response, long duration, String correlationId) {
        int status = response.getStatus();
        
        logger.info("=== RESPONSE END === Status: {} - Duration: {}ms - Correlation ID: {}", 
                   status, duration, correlationId);
        
        // Log response headers
        if (loggingConfig.isLogResponseHeaders()) {
            Map<String, String> responseHeaders = getResponseHeaders(response);
            if (!responseHeaders.isEmpty()) {
                logger.debug("Response Headers: {}", responseHeaders);
            }
        }
        
        // Log response body
        if (loggingConfig.isLogResponseBody()) {
            String responseBody = getResponseBody(response);
            if (responseBody != null && !responseBody.trim().isEmpty()) {
                if (status >= 400) {
                    logger.error("Error Response Body: {}", responseBody);
                } else {
                    logger.info("Response Body: {}", responseBody);
                }
            }
        }
    }
    
    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            
            // Filter out sensitive headers
            if (isSensitiveHeader(headerName)) {
                headers.put(headerName, "[REDACTED]");
            } else {
                headers.put(headerName, headerValue);
            }
        }
        
        return headers;
    }
    
    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        
        for (String headerName : response.getHeaderNames()) {
            String headerValue = response.getHeader(headerName);
            if (isSensitiveHeader(headerName)) {
                headers.put(headerName, "[REDACTED]");
            } else {
                headers.put(headerName, headerValue);
            }
        }
        
        return headers;
    }
    
    private boolean isSensitiveHeader(String headerName) {
        String lowerCaseName = headerName.toLowerCase();
        return lowerCaseName.contains("authorization") ||
               lowerCaseName.contains("password") ||
               lowerCaseName.contains("secret") ||
               lowerCaseName.contains("token") ||
               lowerCaseName.contains("api-key");
    }
    
    private boolean shouldLogRequestBody(String method) {
        return "POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method);
    }
    
    private String getRequestBody(ContentCachingRequestWrapper request) {
        try {
            byte[] content = request.getContentAsByteArray();
            if (content.length > 0) {
                String body = new String(content, StandardCharsets.UTF_8);
                // Limit body size to prevent log flooding
                int maxSize = loggingConfig.getMaxBodySize();
                return body.length() > maxSize ? body.substring(0, maxSize) + "...[TRUNCATED]" : body;
            }
        } catch (Exception e) {
            logger.warn("Failed to read request body: {}", e.getMessage());
        }
        return null;
    }
    
    private String getResponseBody(ContentCachingResponseWrapper response) {
        try {
            byte[] content = response.getContentAsByteArray();
            if (content.length > 0) {
                String body = new String(content, StandardCharsets.UTF_8);
                // Limit body size to prevent log flooding
                int maxSize = loggingConfig.getMaxBodySize();
                return body.length() > maxSize ? body.substring(0, maxSize) + "...[TRUNCATED]" : body;
            }
        } catch (Exception e) {
            logger.warn("Failed to read response body: {}", e.getMessage());
        }
        return null;
    }
    
    private String getOrGenerateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }
}