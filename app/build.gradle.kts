plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.navigationSafeArgs)
    alias(libs.plugins.hiltAndroid)
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
                proguardRulesFile
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.room)
    implementation(libs.hilt)
    implementation(libs.kotlin.stdlib)
    implementation(libs.material)

    kapt(libs.hilt.compiler)

    testImplementation(libs.jUnit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)

    debugImplementation(libs.leakCanary)
}