plugins {
    id ("com.android.library")
    kotlin ("android")
}

android {
    namespace = "com.e444er.common_ui_res"
    compileSdk = AndroidConfig.compileSdkVersion

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion

        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
        consumerProguardFiles(AndroidConfig.consumerProguardFiles)
    }
}