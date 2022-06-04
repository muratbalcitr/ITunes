plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    kotlin(Plugins.androidExtensions)
}
android {
    compileSdk = (Configs.compileSdkVersion)
    buildToolsVersion = Configs.buildToolsVersion

    defaultConfig {
        minSdk = (Configs.minSdkVersion)
        targetSdk = (Configs.targetSdkVersion)

        testInstrumentationRunner = Configs.testInstrumentationRunner
    }

    sourceSets {
        getByName(Flavors.Default.MAIN) {
            java.srcDir("src/main/kotlin")
        }
    }

    buildTypes {
        getByName(Flavors.BuildTypes.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.Kotlin.kotlinCoroutinesCore)
    implementation(Dependencies.Kotlin.kotlinCoroutinesAndroid)

    // Android
    implementation(Dependencies.Android.androidCore)

    // Network
    api(Dependencies.Network.gson)
    api(Dependencies.Network.gsonAdapter)
    api(Dependencies.Network.retrofit)
    api(Dependencies.Network.rxJavaAdapter)
    api(Dependencies.Network.okHttp)
    api(Dependencies.Network.loggingInterceptor)
    api(Dependencies.Network.pagingRuntime)

    // Daager-Hilt
    api(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)

    // For instrumentation tests
    androidTestImplementation(Dependencies.DI.hiltAndroidTesting)
    kaptAndroidTest(Dependencies.DI.hiltCompiler)

    // For local unit tests
    testImplementation(Dependencies.DI.hiltAndroidTesting)
    kaptTest(Dependencies.DI.hiltCompiler)

    // Testing
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)
}

kapt {
    correctErrorTypes = true
}
