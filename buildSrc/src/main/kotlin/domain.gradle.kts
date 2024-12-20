plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    implementation(libs.javaxInject())
    implementation(libs.kotlinStdlib())
    implementation(libs.kotlinxCoroutinesCore())
}
