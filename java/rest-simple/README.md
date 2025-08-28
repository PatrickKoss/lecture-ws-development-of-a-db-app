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

Metrics Endpoint:

- http://localhost:8081/actuator/prometheus

# VS Code Extensions

The Docker container includes the following VS Code extensions for optimal Java development:

## Core Java Development

- **Java Extension Pack** (`vscjava.vscode-java-pack`) - Complete Java development toolkit
- **Language Support for Java** (`redhat.java`) - Core Java language features

## Spring Boot

- **Spring Boot Tools** (`vmware.vscode-spring-boot`) - Spring Boot development support
- **Spring Boot Dashboard** (`vscjava.vscode-spring-boot-dashboard`) - Application management

## Database & File Formats

- **SQLite** (`alexcvzz.vscode-sqlite`) - SQLite database viewer and editor
- **XML** (`redhat.vscode-xml`) - XML language support
- **YAML** (`redhat.vscode-yaml`) - YAML language support

## Development Productivity

- **IntelliJ IDEA Keybindings** (`k--kato.intellij-idea-keybindings`) - Familiar IntelliJ shortcuts
- **Shift Shift** (`ahmedradwan.shift-shift`) - Quick file search with Shift+Shift (like IntelliJ)
- **GitLens** (`eamodio.gitlens`) - Enhanced Git integration
- **JSON** (`ms-vscode.vscode-json`) - JSON language support
- **Material Icon Theme** (`PKief.material-icon-theme`) - File and folder icons
- **Auto Rename Tag** (`formulahendry.auto-rename-tag`) - Automatically rename paired HTML/XML tags
- **Indent Rainbow** (`oderwat.indent-rainbow`) - Colorize indentation levels

## API Testing

- **REST Client** (`humao.rest-client`) - Test REST APIs directly in VS Code
- **Thunder Client** (`rangav.vscode-thunder-client`) - Alternative API testing tool

## Code Quality

- **TODO Highlight** (`wayou.vscode-todo-highlight`) - Highlight TODO comments
- **Code Spell Checker** (`streetsidesoftware.code-spell-checker`) - Spell checking for code

## IntelliJ-like Experience

The container is configured with:

- **IntelliJ IDEA Keybindings** for familiar shortcuts
- **Shift+Shift** for quick file search (just like IntelliJ's "Search Everywhere")
- Automatic import organization and formatting on save
