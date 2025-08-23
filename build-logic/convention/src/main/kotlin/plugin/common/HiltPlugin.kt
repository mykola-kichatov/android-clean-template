package plugin.common

import hilt
import hiltCompiler
import implementation
import ksp
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.hilt())
                ksp(libs.hiltCompiler())
            }
        }
    }
}