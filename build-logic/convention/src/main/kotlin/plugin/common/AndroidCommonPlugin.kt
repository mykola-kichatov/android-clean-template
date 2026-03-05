package plugin.common

import Constants
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class AppAndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jlleitschuh.gradle.ktlint")
            }
            configure(extensions.getByType<ApplicationExtension>())
        }
    }
}

internal class LibAndroidCommonPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jlleitschuh.gradle.ktlint")
            }
            configure(extensions.getByType<LibraryExtension>())
        }
    }
}

private fun Project.configure(ext: ApplicationExtension) {
    ext.apply {
        compileSdk = Constants.Main.COMPILE_SDK
        defaultConfig {
            minSdk = Constants.Main.MIN_SDK
            testInstrumentationRunner = Constants.TESTS_RUNNER
        }
        lint {
            checkReleaseBuilds = true
            abortOnError = true
        }
        compileOptions {
            sourceCompatibility = Constants.javaVersion
            targetCompatibility = Constants.javaVersion
        }
    }
    configureKotlin()
}

private fun Project.configure(ext: LibraryExtension) {
    ext.apply {
        compileSdk = Constants.Main.COMPILE_SDK
        defaultConfig {
            minSdk = Constants.Main.MIN_SDK
        }
        buildFeatures {
            buildConfig = false
        }
        lint {
            checkReleaseBuilds = true
            abortOnError = true
        }
        compileOptions {
            sourceCompatibility = Constants.javaVersion
            targetCompatibility = Constants.javaVersion
        }
    }
    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(Constants.kotlinLanguageVersion)
        }
    }
}
