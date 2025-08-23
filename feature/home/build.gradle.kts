plugins {
    alias(libs.plugins.feature)
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
    implementation(project(":feature:element-details"))
    implementation(project(":feature:element-list"))
    implementation(project(":feature:settings"))
}
