package plugin

import implementation
import javaxInject
import kotlinStdlib
import kotlinxCoroutinesCore
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class DomainPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            dependencies {
                implementation(libs.javaxInject())
                implementation(libs.kotlinStdlib())
                implementation(libs.kotlinxCoroutinesCore())
            }
        }
    }
}