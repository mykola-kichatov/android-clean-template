plugins {
    alias(libs.plugins.feature)
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))
    implementation(project(":feature:common"))
}
