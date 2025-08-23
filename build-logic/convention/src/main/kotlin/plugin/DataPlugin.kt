package plugin

import androidxCore
import androidxRoom
import androidxRoomCompiler
import asNamespace
import com.android.build.api.dsl.LibraryExtension
import com.google.devtools.ksp.gradle.KspExtension
import implementation
import javaxInject
import kotlinxCoroutinesAndroid
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
import retrofitGson

internal class DataPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(LibAndroidCommonPlugin::class.java)
                apply(HiltPlugin::class.java)
                apply(UnitTestsPlugin::class.java)
            }

            extensions.getByType<LibraryExtension>().apply {
                namespace = target.asNamespace()
            }

            extensions.getByType<KspExtension>().apply {
                arg("room.schemaLocation", "$projectDir/schemas")
            }

            dependencies {
                implementation(libs.androidxCore())
                implementation(libs.androidxRoom())
                implementation(libs.javaxInject())
                implementation(libs.kotlinxCoroutinesAndroid())
                implementation(libs.retrofit())
                implementation(libs.retrofitGson())

                ksp(libs.androidxRoomCompiler())
            }
        }
    }
}
