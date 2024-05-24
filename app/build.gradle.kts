plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "ru.ikom.products"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.ikom.products"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":core:network"))
    implementation(project(":features:catalog"))
    implementation(project(":data:products"))
    implementation(project(":features:details"))

    implementation(libs.dagger)
    implementation(project(":core:database"))
    implementation(project(":features:basket"))
    ksp(libs.dagger.compiler)

    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization)
    implementation(libs.converter.kotlinx.serialization)

    implementation(libs.coil)

    implementation(libs.fragment.ktx)
}