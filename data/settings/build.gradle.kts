plugins {
    alias(libs.plugins.data)
}

dependencies {
    implementation(project(":domain:settings"))

    implementation(libs.androidx.datastore.preferences)
}
