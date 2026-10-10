plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))
    implementation(project(":ui:common"))
}
