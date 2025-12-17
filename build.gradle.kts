// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.sonarqube") version "2.5"
}

sonarqube {
    properties {
        property("sonar.projectName", "SonarTesting")
        property("sonar.projectKey", "mrehman-hub_SonarTesting")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.login", System.getenv("SONAR_TOKEN"))
        property("sonar.sources","src/main/java")
        property("sonar.language","java")
        property("sonar.sourceEncoding", "UTF-8")
    }
}