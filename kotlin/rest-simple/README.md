# Introduction

1. This project was generated with the spring cli:

```bash
spring init --build=gradle --java-version=17 --dependencies=web,data-jpa,h2,flyway rest-simple --type=gradle-project-kotlin -l kotlin
```

2. Add the sqlite dependency to the build.gradle.kts file:

```kotlin
dependencies {
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("com.github.gwenn:sqlite-dialect:0.1.1")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("junit:junit:4.13.1")
    testImplementation("junit:junit:4.13.1")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:3.12.4")
    implementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}
```

3. Add properties to the [application.properties](./src/main/resources/application.properties) file:

```properties
spring.jpa.database-platform=org.sqlite.hibernate.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:sqlite:students.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.username=admin
spring.datasource.password=admin
server.port=8081
```

4. Add Controller, Repository, Exception, and Model classes.

# Run

1. Run the application:

```bash
./gradlew bootJar
java -jar build/libs/rest-simple-0.0.1-SNAPSHOT.jar
```

2. Visit the swagger ui:

```bash
http://localhost:8081/swagger-ui/
```
