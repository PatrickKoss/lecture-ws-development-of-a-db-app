package com.example.restsimple.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    public MetricsConfiguration(MeterRegistry meterRegistry) {
        // Initialize counters to ensure they exist in Prometheus
        meterRegistry.counter("http_requests_total", "method", "unknown", "path", "unknown", "status", "unknown");
        meterRegistry.counter("http_requests_2xx_total", "method", "unknown", "path", "unknown", "status", "unknown");
        meterRegistry.counter("http_requests_4xx_total", "method", "unknown", "path", "unknown", "status", "unknown");
        meterRegistry.counter("http_requests_5xx_total", "method", "unknown", "path", "unknown", "status", "unknown");
    }
}