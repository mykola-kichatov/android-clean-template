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
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

        if (this is LibraryExtension) {
            buildFeatures {
                buildConfig = false
            }
        }

        lint {
            checkReleaseBuilds = true
            abortOnError = true
        }

        compileOptions {
            sourceCompatibility = Constants.javaVersion
            targetCompatibility = Constants.javaVersion
        }

        this@configure.extensions.getByType<KotlinAndroidProjectExtension>().apply {
            jvmToolchain(Constants.javaVersion.majorVersion.toInt())
        }

        this@configure.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(Constants.kotlinJvmTarget))
            }
        }

        dependencies {
            implementation(libs.kotlinStdlib())
        }
    }
}
