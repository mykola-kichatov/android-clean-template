plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

java {
    toolchain {
        languageVersion = Constants.javaVersion
    }
}

android {
    this.compileSdk = Constants.Main.compileSdk

    defaultConfig {
        this.minSdk = Constants.Main.minSdk
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidxCore())
    implementation(libs.androidxLifecycleRuntime())
    implementation(libs.androidxLifecycleRuntimeCompose())

    implementation(libs.androidxNavigationCompose())
    implementation(libs.coil())
    implementation(libs.composeActivity())
    implementation(libs.composeAnimation())
    implementation(platform(libs.composeBom()))
    implementation(libs.composeFoundation())
    implementation(libs.composeMaterial3())
    implementation(libs.composeUi())
    implementation(libs.composeUiGraphics())
    implementation(libs.composeUiToolingPreview())
    implementation(libs.composeViewmodel())
    implementation(libs.hilt())
    implementation(libs.hiltNavigationCompose())
    implementation(libs.kotlinStdlib())

    ksp(libs.hiltCompiler())

    testImplementation(libs.bundleUnitTesting())
    testRuntimeOnly(libs.jUnit5Engine())

    debugImplementation(libs.composeUiTooling())
    debugImplementation(libs.composeUiTestManifest())

    androidTestImplementation(libs.bundleAndroidTesting())
    androidTestImplementation(platform(libs.composeBom()))
}
