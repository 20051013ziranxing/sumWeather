plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sunnyweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sunnyweather"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        dataBinding {
            enable = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("com.google.code.gson:gson:2.6.2")
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
    implementation(files("../libs/AMap3DMap_10.0.800_AMapSearch_9.7.2_AMapLocation_6.4.5_20240718.jar"))
    implementation(project(":mylibrary"))
    implementation("androidx.room:room-runtime:2.4.3")
    annotationProcessor("androidx.room:room-compiler:2.4.3")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}