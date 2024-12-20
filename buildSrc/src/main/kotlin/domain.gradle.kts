plugins {
    id("java-library")
    kotlin("jvm")
}

java {
    val javaVersion: JavaVersion by rootProject.extra
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

dependencies {
    implementation(libs.javaxInject())
    implementation(libs.kotlinStdlib())
    implementation(libs.kotlinxCoroutinesCore())
}
