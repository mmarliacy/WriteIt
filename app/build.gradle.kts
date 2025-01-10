plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.projects.writeit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.projects.writeit"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
// New implementation
dependencies {
    //--> Foundation
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.core.ktx)

    //--> Compose BOM with a version definition - Nomenclature
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)

    //--> UI & Graphics
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.ui)
    implementation(libs.ui.tooling)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.material)

    //--> LifeCycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //--> Tests & Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    testImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose.v240beta01)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //Dagger - Hilt
    implementation(libs.hilt.android.v249)
    ksp(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.lifecycle.viewmodel)
    ksp(libs.androidx.hilt.compiler)

    // Room
    implementation (libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler.v230)

    // Kotlin Extensions and Coroutines support for Room
    implementation (libs.androidx.room.ktx)
}