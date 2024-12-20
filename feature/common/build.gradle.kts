plugins {
    id("feature")
}

android {
    val applicationId: String by rootProject.extra
    namespace = "$applicationId.feature.common"
}

dependencies {
    implementation(project(":domain:common"))
}
