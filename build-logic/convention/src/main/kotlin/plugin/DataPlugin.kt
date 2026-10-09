package plugin

import androidTestImplementation
import androidx.room.gradle.RoomExtension
import androidxCore
import androidxRoom
import androidxRoomCompiler
import asNamespace
import bundleDataAndroidTesting
import com.android.build.api.dsl.LibraryExtension
import implementation
import kotlinxCoroutinesAndroid
import kotlinxSerializationJson
import ksp
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import plugin.common.HiltPlugin
import plugin.common.LibAndroidCommonPlugin
import plugin.common.UnitTestsPlugin
import retrofit
import retrofitKotlinxSerialization

internal class DataPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(LibAndroidCommonPlugin::class.java)
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply(HiltPlugin::class.java)
                apply("androidx.room")
                apply(UnitTestsPlugin::class.java)
            }

            extensions.getByType<LibraryExtension>().apply {
                namespace = target.asNamespace()
            }

            extensions.getByType<RoomExtension>().apply {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                implementation(libs.androidxCore())
                implementation(libs.androidxRoom())
                implementation(libs.kotlinxCoroutinesAndroid())
                implementation(libs.kotlinxSerializationJson())
                implementation(libs.retrofit())
                implementation(libs.retrofitKotlinxSerialization())

                ksp(libs.androidxRoomCompiler())

                // MigrationTestHelper runs on the device: every database migration ships with a test.
                androidTestImplementation(libs.bundleDataAndroidTesting())
            }
        }
    }
}
