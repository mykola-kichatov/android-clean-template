plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.common"
}

dependencies {
    implementation(project(":domain:common"))
}
