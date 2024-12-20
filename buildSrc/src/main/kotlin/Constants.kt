import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion

object Constants {

    object Main {
        const val applicationId = "com.mkchtv.cleantemplate"
        const val minSdk = 21
        const val compileSdk = 35
        const val targetSdk = 35
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Proguard {
        const val rulesFile = "proguard-rules.pro"
        const val consumerRulesFile = "consumer-rules.pro"
        const val androidOptimizeFile = "proguard-android-optimize.txt"
    }

    val javaVersion = JavaLanguageVersion.of(17)
    const val testsRunner = "androidx.test.runner.AndroidJUnitRunner"
}
