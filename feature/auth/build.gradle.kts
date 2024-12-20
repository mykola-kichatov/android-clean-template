plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.auth"
}

dependencies {
    implementation(project(":domain:auth"))

    implementation(project(":feature:common"))
}
