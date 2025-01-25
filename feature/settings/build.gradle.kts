plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.settings"
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))

    implementation(project(":feature:common"))
}
