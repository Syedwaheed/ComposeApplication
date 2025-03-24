

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"

//    alias(libs.plugins.kotlinAndroidKsp)
//    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.newapp.composeapplicationstart"
    compileSdk = 35
    println("API_KEY from environment: ${System.getenv("API_KEY")}")
    defaultConfig {
        applicationId = "com.newapp.composeapplicationstart"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        debug {
            isMinifyEnabled = false
            isDebuggable = true
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
        buildConfig = true
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "/META-INF/{AL2.0,LGPL2.1}"
            )
        )
    }
    flavorDimensions += "version"
    val apiKey: String = System.getenv("API_KEY") ?: "default_api_key"
//    val apiKey = project.findProperty("API_KEY") as String
    productFlavors {
        create("free"){
            dimension = "version"
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String","TMDB_IMAGE_BASER_URL","\"${project.findProperty("TMDB_IMAGE_BASE_URL")}\"")
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"

        }
        create("paid"){
            dimension = "version"
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
            buildConfigField("String","TMDB_IMAGE_BASER_URL","\"${project.findProperty("TMDB_IMAGE_BASE_URL")}\"")

            applicationIdSuffix = ".paid"
            versionNameSuffix = "-paid"
        }
    }
    sourceSets {
        getByName("debug"){
            java.srcDir("src/debug/java")
        }
    }
//    testOptions {
//        packaging {
//            resources.excludes.add("META-INF/*")
//        }
//    }
}
//composeCompiler{
//    enableStrongSkippingMode = true
//    reportsDestination = layout.buildDirectory.dir("compose_compiler")
//    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
//}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    debugImplementation(libs.androidx.mockk)
    testImplementation(libs.androidx.mockk)
    androidTestImplementation(libs.androidx.instrumented.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Hilt
    implementation(libs.hilt.android.core)
    ksp(libs.hilt.compiler)

    //ViewModel
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.hilt.compose.viewmodel)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.interceptor)
    //Coroutine
    implementation(libs.androidx.coroutine)
    implementation(libs.coroutine.core)

    //Coil Version
    implementation(libs.androidx.coil)

    //NavigationComponent
    implementation(libs.androidx.navigation)
    //JSON Serialization
    implementation(libs.kotlinx.serialization)

    //SharedPrefEncryption
    implementation(libs.androidx.encrypt.pref)
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.androidx.navigation.testing)
    testImplementation(kotlin("test"))
    testImplementation(libs.google.truth)
}