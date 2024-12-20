plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
}

android {
    val applicationId: String by rootProject.extra
    val compileSdk: Int by rootProject.extra
    val javaVersion: JavaVersion by rootProject.extra

    namespace = "$applicationId.feature.auth"
    this.compileSdk = compileSdk

    defaultConfig {
        val minSdk: Int by rootProject.extra
        val consumerRulesFile: String by rootProject.extra
        val testsRunner: String by rootProject.extra

        this.minSdk = minSdk
        testInstrumentationRunner = testsRunner
        consumerProguardFiles(consumerRulesFile)
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
    implementation(project(":domain:auth"))

    implementation(project(":feature:common"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
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

    ksp(libs.hilt.compiler)

    testImplementation(libs.bundles.unitTesting)
    testRuntimeOnly(libs.junit5.engine)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
