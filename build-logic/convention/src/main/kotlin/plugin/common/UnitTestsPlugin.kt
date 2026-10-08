package plugin.common

import bundleUnitTesting
import byteBuddyAgent
import jUnit5Bom
import jUnit5Engine
import jUnit5Launcher
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.gradle.process.CommandLineArgumentProvider
import testImplementation
import testRuntimeOnly

private const val TESTING_MODULE = ":testing"
private const val BYTE_BUDDY_AGENT = "byteBuddyAgent"

internal class UnitTestsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val agent = configurations.create(BYTE_BUDDY_AGENT) {
                isCanBeConsumed = false
                isCanBeResolved = true
                isTransitive = false
                description = "Byte Buddy's agent, handed to every test JVM with -javaagent."
            }

            tasks.withType<Test> {
                useJUnitPlatform()
                testLogging {
                    events("passed", "skipped", "failed")
                }
                jvmArgumentProviders.add(ByteBuddyAgentArgument(agent))
            }

            dependencies {
                add(BYTE_BUDDY_AGENT, libs.byteBuddyAgent())
                testImplementation(platform(libs.jUnit5Bom()))
                testImplementation(libs.bundleUnitTesting())
                testRuntimeOnly(libs.jUnit5Engine())
                testRuntimeOnly(libs.jUnit5Launcher())
                // :testing uses the domain plugin, which applies this plugin too
                if (path != TESTING_MODULE) {
                    testImplementation(project(TESTING_MODULE))
                }
            }
        }
    }
}

/**
 * Loads Byte Buddy's agent at JVM start, so MockK never has to attach one at runtime.
 *
 * Without it, `ByteBuddyAgent.install()` self-attaches. The JDK refuses that by default, so it
 * falls back to attaching from a spawned process, and that fallback can lose races under load.
 * When it fails, the first test class to hit it breaks every MockK test after it in the same JVM.
 */
internal class ByteBuddyAgentArgument(
    @get:Classpath val agentJar: FileCollection,
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> =
        listOf("-javaagent:${agentJar.singleFile.absolutePath}")
}
