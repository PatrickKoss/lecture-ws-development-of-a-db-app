plugins {
    id("java")
    application
}

group = "org.lecture"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.47.1.0")
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.lecture.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.register<JavaExec>("test-crud") {
    group = "application"
    description = "Run CRUD test"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("org.lecture.TestMain")
}
