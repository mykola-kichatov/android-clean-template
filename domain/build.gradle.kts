plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktlintGradle)
}

java {
    val javaVersion: JavaVersion by rootProject.extra
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)

    ktlintRuleset(project(":ktlint-rules"))
}
