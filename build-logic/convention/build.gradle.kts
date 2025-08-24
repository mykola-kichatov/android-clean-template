plugins {
    `kotlin-dsl`
}

group = "com.mkchtv.cleantemplate.buildlogic"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.hilt.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.ktlint.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("app") {
            id = "com.mkchtv.convention.app"
            implementationClass = "plugin.AppPlugin"
        }
        create("domain") {
            id = "com.mkchtv.convention.domain"
            implementationClass = "plugin.DomainPlugin"
        }
        create("data") {
            id = "com.mkchtv.convention.data"
            implementationClass = "plugin.DataPlugin"
        }
        create("feature") {
            id = "com.mkchtv.convention.feature"
            implementationClass = "plugin.FeaturePlugin"
        }
    }
}