package course.pizzadelivery;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  OpenAPI apiMetadata() {
    return new OpenAPI()
        .info(
            new Info()
                .title("pizza-delivery REST API")
                .version("1.0.0")
                .license(new License().name("MIT").url("https://opensource.org/license/mit")))
        .servers(List.of(new Server().url("/").description("Aktueller Server")))
        .security(List.of());
  }

  @Bean
  OpenApiCustomizer correlationIdResponseHeader() {
    return openApi ->
        openApi
            .getPaths()
            .values()
            .forEach(
                path ->
                    path.readOperations()
                        .forEach(
                            operation -> {
                              operation.setSecurity(List.of());
                              operation
                                  .getResponses()
                                  .values()
                                  .forEach(
                                      response ->
                                          response.addHeaderObject(
                                              "X-Correlation-ID",
                                              new Header()
                                                  .description(
                                                      "ID zur Zuordnung von Client-Antwort und"
                                                          + " Server-Log")
                                                  .schema(new StringSchema())));
                            }));
  }
}
