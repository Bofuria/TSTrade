import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.tstrade"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tstrade"
        minSdk = 24
        targetSdk = 34
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
//            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildToolsVersion = "34.0.0"

    packaging {
        resources.excludes.add("META-INF/**")
        resources.excludes.add("protobuf.meta")
        resources.excludes.add("google/firestore/v1/**")
        resources.excludes.add("google/protobuf/**")
    }
}

dependencies {

//    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.tooling.debug)
    implementation(libs.compose.runtime.livedata)
//    implementation(libs.compose.runtime.viewmodel)
//    implementation(libs.compose.runtime.activity)
    implementation(libs.compose.material)
    implementation(libs.navigation.compose)
    implementation(libs.android.material)


    implementation(libs.hilt.navigation.compose)

//    implementation(libs.dagger.core)
//    ksp(libs.dagger.compiler)

//    api(libs.dagger.android)
//    api(libs.dagger.android.support)
//    ksp(libs.dagger.android.processor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)


//    implementation(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)

}

