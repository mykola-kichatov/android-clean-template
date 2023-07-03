plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.hiltAndroid) apply false
}

val applicationId by extra("com.mkchtv.cleantemplate")
val minSdk by extra(21)
val compileSdk by extra(33)
val targetSdk by extra(33)
val versionCode by extra(1)
val versionName by extra("1.0")
val proguardRulesFile by extra("proguard-rules.pro")
val consumerRulesFile by extra("consumer-rules.pro")
val proguardAndroidOptimizeFile by extra("proguard-android-optimize.txt")
val javaVersion by extra(JavaVersion.VERSION_17)
val testsRunner by extra("androidx.test.runner.AndroidJUnitRunner")