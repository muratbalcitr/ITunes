import org.gradle.api.artifacts.dsl.DependencyHandler

// DI
object Dependencies {

    object Kotlin {
        val kotlinStdLib by lazy {
            "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.kotlinStdLib}"
        }
        val kotlinCoroutinesCore by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.kotlinCoroutinesCore}"
        }
        val kotlinCoroutinesAndroid by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.kotlinCoroutinesCore}"
        }

        object Test {
            val common by lazy {
                "org.jetbrains.kotlin:kotlin-test-common:${Versions.Kotlin.kotlinVersion}"
            }
            val annotations by lazy {
                "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.Kotlin.kotlinVersion}"
            }
            val junit by lazy {
                "org.jetbrains.kotlin:kotlin-test-junit:${Versions.Kotlin.kotlinVersion}"
            }
        }
    }

    object Android {
        val androidCoreKtx by lazy {
            "androidx.core:core-ktx:${Versions.Android.androidCore}"
        }
        val androidCore by lazy {
            "androidx.core:core:${Versions.Android.androidCore}"
        }
        val appCompat by lazy {
            "androidx.appcompat:appcompat:${Versions.Android.appCompat}"
        }
        val legacySupport by lazy {
            "androidx.legacy:legacy-support-v4:${Versions.Android.legacySupport}"
        }
        val multidex by lazy {
            "androidx.multidex:multidex:${Versions.Android.multiDex}"
        }
        val materialDesign by lazy {
            "com.google.android.material:material:${Versions.Android.materialDesign}"
        }
        val fragment by lazy {
            "androidx.fragment:fragment-ktx:${Versions.Android.fragmentVersion}"
        }
        val constraintLayout by lazy {
            "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        }
        val recyclerView by lazy {
            "androidx.recyclerview:recyclerview:${Versions.Android.recyclerView}"
        }

        val cardView by lazy {
            "androidx.cardview:cardview:${Versions.Android.cardView}"
        }
    }

    object Coroutines {

        val coroutinesAandroid by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1"
        }
        val kotlinCoroutinesAdapter by lazy {
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
        }
        val coroutinesTest by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1"
        }
        val coroutinesCore by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
        }
    }

    object Tools {

        val timber by lazy {
            "com.jakewharton.timber:timber:${Versions.Tools.timber}"
        }
    }

    object Navigation {
        val runTimeNavigation by lazy {
            "androidx.navigation:navigation-runtime-ktx:${Versions.Navigation.runTimeNavigation}"
        }
        val navigationFragment by lazy {
            "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation.navigationFragment}"
        }

        val navigationUi by lazy {
            "androidx.navigation:navigation-ui-ktx:${Versions.Navigation.navigationUI}"
        }
    }

    object LifeCycle {
        val runTimeLiveCycle by lazy {
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifeCycle.runTimeLifeCycle}"
        }
        val lifeCycleCompiler by lazy {
            "androidx.lifecycle:lifecycle-compiler:${Versions.LifeCycle.viewModelState}"
        }
        val liveData by lazy {
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LifeCycle.liveData}"
        }
        val viewModel by lazy {
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LifeCycle.viewModel}"
        }
    }

    object DI {

        val hilt by lazy {
            "com.google.dagger:hilt-android:${Versions.DI.hilt}"
        }
        val hiltCompiler by lazy {
            "com.google.dagger:hilt-compiler:${Versions.DI.hilt}"
        }

        val hiltAndroidTesting by lazy {
            "com.google.dagger:hilt-android-testing:${Versions.DI.hilt}"
        }
    }

    object Network {
        val gson by lazy {
            "com.google.code.gson:gson:${Versions.Network.gson}"
        }
        val gsonAdapter by lazy {
            "com.squareup.retrofit2:converter-gson:${Versions.Network.gsonConverter}"
        }
        val retrofit by lazy {
            "com.squareup.retrofit2:retrofit:${Versions.Network.retrofit}"
        }
        val rxJavaAdapter by lazy {
            "com.squareup.retrofit2:adapter-rxjava3:${Versions.Network.rxJava3Adapter}"
        }
        val okHttp by lazy {
            "com.squareup.okhttp3:okhttp:${Versions.Network.okHttp}"
        }
        val loggingInterceptor by lazy {
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.loggingInterceptor}"
        }
        val pagingRuntime by lazy {
            "androidx.paging:paging-runtime-ktx:${Versions.Network.paging}"
        }
    }

    object Glide {
        val glide by lazy {
            "com.github.bumptech.glide:glide:${Versions.Glide.core}"
        }
        val glideCompiler by lazy {
            "com.github.bumptech.glide:compiler:${Versions.Glide.core}"
        }
    }

    object Project {
        fun DependencyHandler.app() = project(mapOf("path" to ":app"))
        fun DependencyHandler.data() = project(mapOf("path" to ":data"))
    }

    object Test {
        val junit by lazy {
            "junit:junit:${Versions.Test.junit}"
        }
        val androidJunit by lazy {
            "androidx.test.ext:junit:${Versions.Test.androidJunit}"
        }
        val espressoCore by lazy {
            "androidx.test.espresso:espresso-core:${Versions.Test.espressoCore}"
        }
        val truthExt by lazy {
            "androidx.test.ext:truth:${Versions.Test.truthExtVersion}"
        }
        val mockK by lazy {
            "io.mockk:mockk:${Versions.Test.mockKVersion}"
        }
        val coreTesting by lazy {
            "androidx.arch.core:core-testing:${Versions.Test.coreTestingVersion}"
        }
    }
}
