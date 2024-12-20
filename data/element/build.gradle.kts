plugins {
    id("data")
}

android {
    val applicationId: String by rootProject.extra
    namespace = "$applicationId.data.element"
}

dependencies {
    implementation(project(":domain:element"))
}
