plugins {
    id("app")
}

dependencies {
    implementation(project(":data:auth"))
    implementation(project(":data:element"))

    implementation(project(":domain:auth"))
    implementation(project(":domain:common"))
    implementation(project(":domain:element"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:common"))
    implementation(project(":feature:element-details"))
    implementation(project(":feature:element-list"))
}
