plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(project(":ui:auth"))
    implementation(project(":ui:common"))
    implementation(project(":ui:element-details"))
    implementation(project(":ui:element-list"))
    implementation(project(":ui:settings"))
}
