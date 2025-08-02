# Introduction

This is the java implementation of the simple rest api. It was created using the spring cli.

```bash
spring init --build=gradle --java-version=17 --dependencies=web,data-jpa,h2,flyway rest-simple --type=gradle-project-kotlin
```

# Getting Started

Run the application:

```bash
make run
```

Run the test suite:

```bash
make test
```

Visit the swagger ui:

Try these URLs in order:
1. http://localhost:8081/swagger-ui.html
2. http://localhost:8081/swagger-ui/index.html
3. http://localhost:8081/swagger-ui/

API Documentation:
- OpenAPI JSON: http://localhost:8081/v3/api-docs
