plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    kotlin("kapt")
}

android {
    namespace = "com.example.interviewalphab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.interviewalphab"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            viewBinding = true
            //noinspection DataBindingWithoutKapt
            dataBinding = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.activity.compose.v170)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.constraintlayout.compose)


    implementation(libs.androidx.foundation)
    implementation(libs.koin.androidx.compose)

    implementation(libs.androidx.browser)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.navigation.compose.v280)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.compose)
    implementation(libs.glide.transformations)

    implementation(libs.accompanist.flowlayout)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.insets)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.serialization.json)
    implementation(libs.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.retrofit)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.dagger.v2481)
    kapt(libs.dagger.compiler.v2481)
}