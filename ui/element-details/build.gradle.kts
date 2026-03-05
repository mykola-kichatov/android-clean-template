plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(libs.coil)
    implementation(project(":domain:common"))
    implementation(project(":domain:element"))
    implementation(project(":ui:auth"))
    implementation(project(":ui:common"))
}
