plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lostfound2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lostfound2"
        minSdk = 28
        targetSdk = 35
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("com.google.android.gms:play-services-maps:17.0.0")  // Google Maps SDK
    implementation("com.google.android.gms:play-services-location:18.0.0")  // Location Services SDK
    implementation("com.google.android.libraries.places:places:2.7.0")    // Places SDK
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

