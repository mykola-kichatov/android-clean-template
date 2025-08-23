plugins {
    alias(libs.plugins.feature)
}

dependencies {
    implementation(project(":domain:element"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
}
