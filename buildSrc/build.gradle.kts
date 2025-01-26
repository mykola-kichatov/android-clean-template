plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Important: This will add the Gradle plugins to the project's entire classpath
    implementation(libs.android.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.compose.compiler.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.ktlint.gradle.plugin)
}
