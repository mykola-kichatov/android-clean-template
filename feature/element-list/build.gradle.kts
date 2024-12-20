plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.elementlist"
}

dependencies {
    implementation(project(":domain:element"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
}
