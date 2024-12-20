plugins {
    id("feature")
}

android {
    val applicationId: String by rootProject.extra
    namespace = "$applicationId.feature.auth"
}

dependencies {
    implementation(project(":domain:auth"))

    implementation(project(":feature:common"))
}
