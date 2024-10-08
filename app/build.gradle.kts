plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
}

android {
    val applicationId: String by rootProject.extra
    val compileSdk: Int by rootProject.extra
    val javaVersion: JavaVersion by rootProject.extra

    namespace = applicationId
    this.compileSdk = compileSdk

    defaultConfig {
        val minSdk: Int by rootProject.extra
        val targetSdk: Int by rootProject.extra
        val versionCode: Int by rootProject.extra
        val versionName: String by rootProject.extra
        val testsRunner: String by rootProject.extra

        this.applicationId = applicationId
        this.minSdk = minSdk
        this.targetSdk = targetSdk
        this.versionCode = versionCode
        this.versionName = versionName
        testInstrumentationRunner = testsRunner
        multiDexEnabled = true
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
        viewBinding = true
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
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room)
    implementation(libs.coil)
    implementation(libs.compose.activity)
    implementation(libs.compose.animation)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.viewmodel)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlin.stdlib)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    ksp(libs.hilt.compiler)

    testImplementation(libs.bundles.unitTesting)
    testRuntimeOnly(libs.junit5.engine)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
