plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.hydra_hymail"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hydra_hymail"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GOOGLE_API_KEY", "\"${property("GOOGLE_API_KEY")}\"")

        buildConfigField("String", "GOOGLE_PLACES_API_KEY", "\"${property("GOOGLE_PLACES_API_KEY")}\"")
        buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"${property("GOOGLE_MAPS_API_KEY")}\"")

        buildConfigField ("String", "LOCAL_BASE_URL", "\"${property("LOCAL_BASE_URL")}\"")
        buildConfigField ("String", "PROD_BASE_URL", "\"${property("PROD_BASE_URL")}\"")


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
        buildConfig = true
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
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.material)
    implementation(libs.play.services.maps)
    implementation(libs.google.firebase.functions.ktx)
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Import BOM first
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

// Firebase services (no versions needed)
    implementation(libs.firebase.functions.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")


    // Google Play Services Auth (needed for Google SSO)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Apple Sign-In support
    implementation("androidx.browser:browser:1.7.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // Retrofit + Moshi/Gson
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    implementation ("com.google.android.gms:play-services-maps:19.2.0")
    implementation( "com.google.android.gms:play-services-location:21.0.1")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Location
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Biometric Auth
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    // OSM maps
    implementation("org.osmdroid:osmdroid-android:6.1.15")

    // Ktor (if you need it)
    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-cio:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
}
