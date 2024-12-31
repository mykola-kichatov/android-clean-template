plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.elementdetails"
}

dependencies {
    implementation(project(":domain:common"))
    implementation(project(":domain:element"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
}
