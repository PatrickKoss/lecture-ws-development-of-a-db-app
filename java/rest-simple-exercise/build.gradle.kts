plugins {
    java
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.github.ben-manes.versions") version "0.38.0"
    application
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
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
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("com.example.restsimple.DemoApplication")
}
