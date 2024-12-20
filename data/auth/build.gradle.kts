plugins {
    id("data")
}

android {
    namespace = "${Constants.Main.applicationId}.data.auth"
}

dependencies {
    implementation(project(":domain:auth"))
}
