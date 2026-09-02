package com.example.restsimple.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class MetricsFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public MetricsFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String method = httpRequest.getMethod();
        String path = httpRequest.getRequestURI();
        
        // Skip actuator endpoints from metrics
        if (path.startsWith("/actuator")) {
            chain.doFilter(request, response);
            return;
        }
        
        chain.doFilter(request, response);
        
        int status = httpResponse.getStatus();
        Tags tags = Tags.of("method", method, "path", path, "status", String.valueOf(status));
        
        // Increment total requests counter
        meterRegistry.counter("http_requests_total", tags).increment();
        
        // Increment specific status range counters
        if (status >= 200 && status < 300) {
            meterRegistry.counter("http_requests_2xx_total", tags).increment();
        } else if (status >= 400 && status < 500) {
            meterRegistry.counter("http_requests_4xx_total", tags).increment();
        } else if (status >= 500) {
            meterRegistry.counter("http_requests_5xx_total", tags).increment();
        }
    }
}