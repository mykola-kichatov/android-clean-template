plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

java {
    toolchain {
        languageVersion = Constants.javaVersion
    }
}

android {
    namespace = Constants.Main.applicationId
    this.compileSdk = Constants.Main.compileSdk

    defaultConfig {
        this.applicationId = Constants.Main.applicationId
        this.minSdk = Constants.Main.minSdk
        this.targetSdk = Constants.Main.targetSdk
        this.versionCode = Constants.Main.versionCode
        this.versionName = Constants.Main.versionName
        testInstrumentationRunner = Constants.testsRunner
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Constants.Proguard.androidOptimizeFile),
                Constants.Proguard.rulesFile,
            )
        }
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = Constants.javaVersion.toString()
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
