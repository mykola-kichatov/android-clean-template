plugins {
    id("data")
}

android {
    val applicationId: String by rootProject.extra
    namespace = "$applicationId.data.auth"
}

dependencies {
    implementation(project(":domain:auth"))
}
