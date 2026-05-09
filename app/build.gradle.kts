import com.android.build.api.dsl.DefaultConfig
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.google.service)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

fun DefaultConfig.buildConfigStrings(vararg pairs: Pair<String, String>) {
    pairs.forEach { (key, value) ->
        manifestPlaceholders[key] = value
        buildConfigField("String", key, "\"$value\"")
    }
}

android {
    namespace = "com.example.heysports"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.heysports"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val props = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
            rootDir, providers
        )

        buildConfigStrings(
            "MAPS_API_KEY" to (props["MAPS_API_KEY"]?.toString() ?: ""),
            "FACEBOOK_APP_ID" to (props["FACEBOOK_APP_ID"]?.toString() ?: ""),
            "FACEBOOK_CLIENT_ID" to (props["FACEBOOK_CLIENT_ID"]?.toString() ?: "")
        )

        buildConfigField("String", "SUPABASE_URL", "\"${props["SUPABASE_URL"]}\"")
        buildConfigField("String", "SUPABASE_KEY", "\"${props["SUPABASE_KEY"]}\"")
        buildConfigField("String", "LOGIN_GOOGLE_KEY", "\"${props["LOGIN_GOOGLE_KEY"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.hilt.work)
    implementation(libs.firebase.components)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.core.splashscreen)


    /**
     * Bellow is all dependencies for app
     * 1. Hilt
     * 2. Hilt Compiler
     * */
    implementation(libs.android.hilt)
    ksp(libs.android.compiler)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.compose.shimmer)


    // Local database
    implementation(libs.androidx.datastore.preferences)

    // Maps service
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)

    implementation(libs.googleid)

    // Database supabase
    val supabaseBom = platform("io.github.jan-tennert.supabase:bom:3.0.3")
    implementation(supabaseBom)
    implementation(libs.postgrest.kt)
    implementation(libs.storage.kt)
    implementation(libs.realtime.kt)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.multiplatform.settings)
    implementation(libs.multiplatform.settings.no.arg)
    implementation(libs.supabase.auth.kt)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
}