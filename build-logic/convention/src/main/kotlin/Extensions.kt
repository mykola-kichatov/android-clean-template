import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType

internal fun DependencyHandler.api(param: Any) = add("api", param)
internal fun DependencyHandler.implementation(param: Any) = add("implementation", param)
internal fun DependencyHandler.testImplementation(param: Any) = add("testImplementation", param)
internal fun DependencyHandler.ksp(param: Any) = add("ksp", param)
internal fun DependencyHandler.testRuntimeOnly(param: Any) = add("testRuntimeOnly", param)
internal fun DependencyHandler.debugImplementation(param: Any) = add("debugImplementation", param)
internal fun DependencyHandler.androidTestImplementation(param: Any) = add("androidTestImplementation", param)

internal fun Project.asNamespace() =
    "${Constants.Main.APP_ID}.${path.removePrefix(":").replace(":", ".").replace("-", ".")}"

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.androidxCore() = getLibrary("androidx-core")
internal fun VersionCatalog.androidxLifecycleRuntime() = getLibrary("androidx-lifecycle-runtime")
internal fun VersionCatalog.androidxLifecycleRuntimeCompose() =
    getLibrary("androidx-lifecycle-runtime-compose")

internal fun VersionCatalog.androidxNavigationCompose() = getLibrary("androidx-navigation-compose")
internal fun VersionCatalog.androidxRoom() = getLibrary("androidx-room")
internal fun VersionCatalog.androidxRoomCompiler() = getLibrary("androidx-room-compiler")
internal fun VersionCatalog.coil() = getLibrary("coil")
internal fun VersionCatalog.composeActivity() = getLibrary("compose-activity")
internal fun VersionCatalog.composeAnimation() = getLibrary("compose-animation")
internal fun VersionCatalog.composeBom() = getLibrary("compose-bom")
internal fun VersionCatalog.composeFoundation() = getLibrary("compose-foundation")
internal fun VersionCatalog.composeMaterial3() = getLibrary("compose-material3")
internal fun VersionCatalog.composeUi() = getLibrary("compose-ui")
internal fun VersionCatalog.composeUiGraphics() = getLibrary("compose-ui-graphics")
internal fun VersionCatalog.composeUiTestManifest() = getLibrary("compose-ui-test-manifest")
internal fun VersionCatalog.composeUiTooling() = getLibrary("compose-ui-tooling")
internal fun VersionCatalog.composeUiToolingPreview() = getLibrary("compose-ui-tooling-preview")
internal fun VersionCatalog.composeViewmodel() = getLibrary("compose-viewmodel")
internal fun VersionCatalog.hilt() = getLibrary("hilt")
internal fun VersionCatalog.hiltNavigationCompose() = getLibrary("hilt-navigation-compose")
internal fun VersionCatalog.hiltCompiler() = getLibrary("hilt-compiler")
internal fun VersionCatalog.javaxInject() = getLibrary("javax-inject")
internal fun VersionCatalog.jUnit5Bom() = getLibrary("junit5-bom")
internal fun VersionCatalog.jUnit5Engine() = getLibrary("junit5-engine")
internal fun VersionCatalog.jUnit5Launcher() = getLibrary("junit5-launcher")
internal fun VersionCatalog.kotlinStdlib() = getLibrary("kotlin-stdlib")
internal fun VersionCatalog.kotlinxCoroutinesAndroid() = getLibrary("kotlinx-coroutines-android")
internal fun VersionCatalog.kotlinxCoroutinesCore() = getLibrary("kotlinx-coroutines-core")
internal fun VersionCatalog.retrofit() = getLibrary("retrofit")
internal fun VersionCatalog.retrofitGson() = getLibrary("retrofit-gson")

internal fun VersionCatalog.bundleAndroidTesting() = getBundle("androidTesting")
internal fun VersionCatalog.bundleUnitTesting() = getBundle("unitTesting")

private fun VersionCatalog.getLibrary(name: String) = findLibrary(name).get()
private fun VersionCatalog.getBundle(name: String) = findBundle(name).get()
