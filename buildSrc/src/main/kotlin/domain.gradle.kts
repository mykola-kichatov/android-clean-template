plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    implementation(libs.javaxInject())
    implementation(libs.kotlinStdlib())
    implementation(libs.kotlinxCoroutinesCore())
}
