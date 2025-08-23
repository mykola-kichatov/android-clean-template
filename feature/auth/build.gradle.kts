plugins {
    alias(libs.plugins.feature)
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":feature:common"))
}
