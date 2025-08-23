package plugin

import androidTestImplementation
import androidxCore
import androidxLifecycleRuntime
import asNamespace
import bundleAndroidTesting
import coil
import com.android.build.api.dsl.LibraryExtension
import implementation
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import plugin.common.HiltPlugin
import plugin.common.LibAndroidCommonPlugin
import plugin.common.LibComposePlugin
import plugin.common.UnitTestsPlugin

internal class FeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(LibAndroidCommonPlugin::class.java)
                apply(LibComposePlugin::class.java)
                apply(HiltPlugin::class.java)
                apply(UnitTestsPlugin::class.java)
            }

            extensions.getByType<LibraryExtension>().apply {
                namespace = target.asNamespace()
            }

            dependencies {
                implementation(libs.androidxCore())
                implementation(libs.androidxLifecycleRuntime())
                implementation(libs.coil())
                androidTestImplementation(libs.bundleAndroidTesting())
            }
        }
    }
}