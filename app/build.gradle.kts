plugins {
    alias(libs.plugins.app)
}

dependencies {
    implementation(libs.androidx.core.splashscreen)

    implementation(project(":data:auth"))
    implementation(project(":data:element"))
    implementation(project(":data:settings"))
    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))
    implementation(project(":domain:element"))
    implementation(project(":domain:settings"))
    implementation(project(":ui:auth"))
    implementation(project(":ui:common"))
    implementation(project(":ui:element-details"))
    implementation(project(":ui:element-list"))
    implementation(project(":ui:home"))
    implementation(project(":ui:settings"))
}
