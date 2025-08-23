package plugin

import Constants
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import plugin.common.AppAndroidCommonPlugin
import plugin.common.AppComposePlugin
import plugin.common.HiltPlugin
import plugin.common.UnitTestsPlugin

internal class AppPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(AppAndroidCommonPlugin::class.java)
                apply(AppComposePlugin::class.java)
                apply(HiltPlugin::class.java)
                apply(UnitTestsPlugin::class.java)
            }

            extensions.getByType<ApplicationExtension>().apply {
                namespace = Constants.Main.APP_ID

                defaultConfig {
                    applicationId = Constants.Main.APP_ID
                    targetSdk = Constants.Main.TARGET_SDK
                    versionCode = Constants.Main.VERSION_CODE
                    versionName = Constants.Main.VERSION_NAME
                    multiDexEnabled = true
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile(Constants.Proguard.ANDROID_OPTIMIZE_FILE),
                            Constants.Proguard.RULES_FILE,
                        )
                    }
                }

            }
        }
    }
}
