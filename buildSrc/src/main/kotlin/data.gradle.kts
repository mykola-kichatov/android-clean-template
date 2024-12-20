plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
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

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
    implementation(libs.androidxRoom())
    implementation(libs.hilt())
    implementation(libs.javaxInject())
    implementation(libs.kotlinStdlib())
    implementation(libs.kotlinxCoroutinesAndroid())
    implementation(libs.retrofit())
    implementation(libs.retrofitGson())

    ksp(libs.androidxRoomCompiler())
    ksp(libs.hiltCompiler())

    testImplementation(libs.bundleUnitTesting())
    testRuntimeOnly(libs.jUnit5Engine())
}
