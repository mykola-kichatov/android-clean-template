plugins {
    alias(libs.plugins.domain)
}

dependencies {
    api(platform(libs.junit5.bom))
    api(libs.junit5.api)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
}
