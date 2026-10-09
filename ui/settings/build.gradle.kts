plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))
    implementation(project(":domain:settings"))
    implementation(project(":ui:common"))
}
