plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    val applicationId: String by rootProject.extra
    val compileSdk: Int by rootProject.extra
    val javaVersion: JavaVersion by rootProject.extra

    this.compileSdk = compileSdk

    defaultConfig {
        val minSdk: Int by rootProject.extra
        val consumerRulesFile: String by rootProject.extra
        val testsRunner: String by rootProject.extra

        this.minSdk = minSdk
        testInstrumentationRunner = testsRunner
        consumerProguardFiles(consumerRulesFile)
    }

    buildTypes {
        val proguardRulesFile: String by rootProject.extra
        val proguardAndroidOptimizeFile: String by rootProject.extra

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(proguardAndroidOptimizeFile),
                proguardRulesFile,
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
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
}
