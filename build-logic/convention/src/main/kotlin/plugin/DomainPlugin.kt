package plugin

import Constants
import implementation
import javaxInject
import kotlinStdlib
import kotlinxCoroutinesCore
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal class DomainPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            extensions.getByType<KotlinJvmProjectExtension>().apply {
                jvmToolchain(Constants.javaVersion.majorVersion.toInt())
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    languageVersion.set(Constants.kotlinVersion)
                }
            }

            dependencies {
                implementation(libs.javaxInject())
                implementation(libs.kotlinStdlib())
                implementation(libs.kotlinxCoroutinesCore())
            }
        }
    }
}
