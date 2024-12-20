plugins {
    id("data")
}

android {
    namespace = "${Constants.Main.applicationId}.data.element"
}

dependencies {
    implementation(project(":domain:element"))
}
