package course.hotel.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
  public static final String HEADER = "X-Correlation-ID";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String incoming = request.getHeader(HEADER);
    String id = incoming == null || incoming.isBlank() ? UUID.randomUUID().toString() : incoming;
    response.setHeader(HEADER, id);
    try (MDC.MDCCloseable ignored = MDC.putCloseable("correlationId", id)) {
      chain.doFilter(request, response);
    }
  }
}
