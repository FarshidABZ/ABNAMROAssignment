plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.abnamro.assignment"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.abnamro.assignment"
        minSdk = libs.versions.minSdk.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":feature:repolist"))
    implementation(project(":feature:repodetail"))

//    compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

//    Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.androidx.core.ktx)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
}