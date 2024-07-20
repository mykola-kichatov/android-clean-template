plugins {
    id("kotlin")
}

dependencies {
    compileOnly(libs.ktlint.ruleset.core)

    testImplementation(libs.bundles.unitTesting)
    testImplementation(libs.ktlint.ruleset.core)
    testImplementation(libs.ktlint.test)
    testRuntimeOnly(libs.slf4j)
}
