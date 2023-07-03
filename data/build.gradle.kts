plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
}

android {
    val applicationId: String by rootProject.extra
    val compileSdk: Int by rootProject.extra
    val javaVersion: JavaVersion by rootProject.extra

    namespace = "$applicationId.data"
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
                proguardRulesFile
            )
        }
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
    implementation(project(":domain"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.room)
    implementation(libs.javax.inject)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.material)

    kapt(libs.androidx.room.compiler)

    testImplementation(libs.jUnit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}