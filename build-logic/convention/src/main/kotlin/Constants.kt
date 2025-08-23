import org.gradle.api.JavaVersion

internal object Constants {

    object Main {
        const val APP_ID = "com.mkchtv.cleantemplate"
        const val MIN_SDK = 24
        const val COMPILE_SDK = 35
        const val TARGET_SDK = 35
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0"
    }

    object Proguard {
        const val RULES_FILE = "proguard-rules.pro"
        const val CONSUMER_RULES_FILE = "consumer-rules.pro"
        const val ANDROID_OPTIMIZE_FILE = "proguard-android-optimize.txt"
    }

    val javaVersion = JavaVersion.VERSION_17
    const val TESTS_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}