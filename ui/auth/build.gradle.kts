plugins {
    alias(libs.plugins.ui)
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":ui:common"))
}
