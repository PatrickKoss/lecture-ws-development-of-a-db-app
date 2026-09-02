package com.example.restsimple.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.logging")
public class LoggingConfiguration {
    
    private boolean detailedRequestLogging = true;
    private boolean logRequestHeaders = true;
    private boolean logResponseHeaders = false;
    private boolean logRequestBody = true;
    private boolean logResponseBody = true;
    private int maxBodySize = 1000;
    
    // Getters and setters
    public boolean isDetailedRequestLogging() {
        return detailedRequestLogging;
    }
    
    public void setDetailedRequestLogging(boolean detailedRequestLogging) {
        this.detailedRequestLogging = detailedRequestLogging;
    }
    
    public boolean isLogRequestHeaders() {
        return logRequestHeaders;
    }
    
    public void setLogRequestHeaders(boolean logRequestHeaders) {
        this.logRequestHeaders = logRequestHeaders;
    }
    
    public boolean isLogResponseHeaders() {
        return logResponseHeaders;
    }
    
    public void setLogResponseHeaders(boolean logResponseHeaders) {
        this.logResponseHeaders = logResponseHeaders;
    }
    
    public boolean isLogRequestBody() {
        return logRequestBody;
    }
    
    public void setLogRequestBody(boolean logRequestBody) {
        this.logRequestBody = logRequestBody;
    }
    
    public boolean isLogResponseBody() {
        return logResponseBody;
    }
    
    public void setLogResponseBody(boolean logResponseBody) {
        this.logResponseBody = logResponseBody;
    }
    
    public int getMaxBodySize() {
        return maxBodySize;
    }
    
    public void setMaxBodySize(int maxBodySize) {
        this.maxBodySize = maxBodySize;
    }
}