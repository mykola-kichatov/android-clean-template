package plugin.common

import Constants
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import implementation
import kotlinStdlib
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal enum class AndroidCommonPluginType {
    APPLICATION,
    LIBRARY
}

internal class AppAndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            applyPlugins(AndroidCommonPluginType.APPLICATION)
            configure(extensions.getByType<ApplicationExtension>())
        }
    }
}

internal class LibAndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            applyPlugins(AndroidCommonPluginType.LIBRARY)
            configure(extensions.getByType<LibraryExtension>())
        }
    }
}

private fun Project.applyPlugins(type: AndroidCommonPluginType) {
    with(pluginManager) {
        when (type) {
            AndroidCommonPluginType.APPLICATION -> apply("com.android.application")
            AndroidCommonPluginType.LIBRARY -> apply("com.android.library")
        }
        apply("org.jetbrains.kotlin.android")
        apply("org.jlleitschuh.gradle.ktlint")
    }
}

private fun Project.configure(commonExtension: CommonExtension<*, *, *, *, *, *>) {

    commonExtension.run {
        compileSdk = Constants.Main.COMPILE_SDK

        defaultConfig {
            minSdk = Constants.Main.MIN_SDK
            testInstrumentationRunner = Constants.TESTS_RUNNER
        }

        compileOptions {
            sourceCompatibility = Constants.javaVersion
            targetCompatibility = Constants.javaVersion
        }

        dependencies {
            implementation(libs.kotlinStdlib())
        }
    }
}
