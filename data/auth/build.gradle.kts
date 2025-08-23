plugins {
    alias(libs.plugins.data)
}

dependencies {
    implementation(project(":domain:auth"))
}
