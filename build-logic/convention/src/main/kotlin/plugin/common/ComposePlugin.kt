package plugin.common

import androidTestImplementation
import androidxLifecycleRuntimeCompose
import androidxNavigationCompose
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import composeActivity
import composeAnimation
import composeBom
import composeFoundation
import composeMaterial3
import composeUi
import composeUiGraphics
import composeUiTestManifest
import composeUiTooling
import composeUiToolingPreview
import composeViewmodel
import debugImplementation
import hiltNavigationCompose
import implementation
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AppComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            configure(extensions.getByType<ApplicationExtension>())
        }
    }
}

internal class LibComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            configure(extensions.getByType<LibraryExtension>())
        }
    }
}

private fun Project.applyPlugins() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }
}

private fun Project.configure(commonExtension: CommonExtension<*, *, *, *, *, *>) {

    commonExtension.run {
        this.buildFeatures {
            compose = true
        }

        dependencies {
            implementation(libs.androidxLifecycleRuntimeCompose())
            implementation(libs.androidxNavigationCompose())
            implementation(libs.composeActivity())
            implementation(libs.composeAnimation())
            implementation(platform(libs.composeBom()))
            implementation(libs.composeFoundation())
            implementation(libs.composeMaterial3())
            implementation(libs.composeUi())
            implementation(libs.composeUiGraphics())
            implementation(libs.composeUiToolingPreview())
            implementation(libs.composeViewmodel())
            debugImplementation(libs.composeUiTooling())
            debugImplementation(libs.composeUiTestManifest())
            androidTestImplementation(platform(libs.composeBom()))
            implementation(libs.hiltNavigationCompose())
        }
    }
}