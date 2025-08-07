pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.spring.io/release")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "io.spring.dependency-management") {
                useModule("io.spring.gradle:dependency-management-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "rest-simple"

