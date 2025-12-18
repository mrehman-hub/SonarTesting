// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.sonarqube") version "7.2.1.6560"
}
sonar {
    properties {
        property("sonar.projectKey", "mrehman-hub_SonarTesting")
        property("sonar.organization", "mrehman-hub")
    }
}