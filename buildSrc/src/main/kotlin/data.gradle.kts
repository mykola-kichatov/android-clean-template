plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jlleitschuh.gradle.ktlint")
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

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
