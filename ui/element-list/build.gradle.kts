plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(project(":domain:element"))
    implementation(project(":ui:auth"))
    implementation(project(":ui:common"))
}
