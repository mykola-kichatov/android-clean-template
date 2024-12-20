plugins {
    id("feature")
}

android {
    val applicationId: String by rootProject.extra
    namespace = "$applicationId.feature.elementdetails"
}

dependencies {
    implementation(project(":domain:common"))
    implementation(project(":domain:element"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
}
