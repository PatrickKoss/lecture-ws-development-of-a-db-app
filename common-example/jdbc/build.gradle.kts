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
    implementation("org.xerial:sqlite-jdbc:3.53.4.0")
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

tasks.register<JavaExec>("runRefactored") {
    group = "application"
    description = "Run the version with a rowToStudent helper"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("org.lecture.MainRefactored")
    standardInput = System.`in`
}
