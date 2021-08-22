package dependencies

import constants.Versions

object ReleaseDependencies {

    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}