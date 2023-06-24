package constants

import org.gradle.api.JavaVersion

object ApplicationBuildConstants {

    const val ID = "com.mkchtv.cleantemplate"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val COMPILE_SDK = 33
    const val MIN_SDK = 19
    const val TARGET_SDK = 33

    const val CONSUMER_PROGUARD_FILES = "consumer-rules.pro"
    const val PROGUARD_RULES_FILE = "proguard-rules.pro"
    const val PROGUARD_ANDROID_OPTIMIZE_FILE = "proguard-android-optimize.txt"

    val JAVA_VERSION_ENUM = JavaVersion.VERSION_17
    val JAVA_VERSION = JAVA_VERSION_ENUM.toString()

}