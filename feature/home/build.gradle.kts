plugins {
    id("feature")
}

android {
    namespace = "${Constants.Main.applicationId}.feature.home"
}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
    implementation(project(":feature:element-details"))
    implementation(project(":feature:element-list"))
    implementation(project(":feature:settings"))
}
