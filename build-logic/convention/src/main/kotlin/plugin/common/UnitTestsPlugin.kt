package plugin.common

import bundleUnitTesting
import jUnit5Bom
import jUnit5Engine
import jUnit5Launcher
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import testImplementation
import testRuntimeOnly

internal class UnitTestsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            tasks.withType<Test> {
                useJUnitPlatform()
                testLogging {
                    events("passed", "skipped", "failed")
                }
            }

            dependencies {
                testImplementation(platform(libs.jUnit5Bom()))
                testImplementation(libs.bundleUnitTesting())
                testRuntimeOnly(libs.jUnit5Engine())
                testRuntimeOnly(libs.jUnit5Launcher())
            }
        }
    }
}