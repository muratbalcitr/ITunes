object Versions {
    object Gradle {
        const val gradleVersion = "7.1.2"
        const val hiltVersion = "2.41"
        const val safeArgs = "2.4.2"
    }

    object Kotlin {
        const val kotlinVersion = "1.5.30"
        const val kotlinStdLib = kotlinVersion
        const val kotlinCoroutinesCore = "1.6.1"
    }

    object Android {
        const val appCompat = "1.4.1"
        const val androidCore = "1.7.0"
        const val legacySupport = "1.0.0"
        const val multiDex = "2.0.1"
        const val materialDesign = "1.5.0"
        const val fragmentVersion = "1.5.0-alpha03"
        const val constraintLayout = "2.1.3"
        const val recyclerView = "1.3.0-alpha01"
        const val recyclerViewSelection = "1.1.0"
        const val cardView = "1.0.0"
        const val palette = "1.0.0"
        const val workManager = "2.5.0"
    }

    object Koin {
        const val koinVersion = "3.1.6"
    }

    object Navigation {
        const val navigationVersion = "2.4.2"
        const val runTimeNavigation = navigationVersion
        const val navigationFragment = navigationVersion
        const val navigationUI = navigationVersion
    }

    object LifeCycle {
        private const val lifecycleVersion = "2.4.1"
        const val runTimeLifeCycle = lifecycleVersion
        const val lifeCycleCompiler = lifecycleVersion
        const val liveData = lifecycleVersion
        const val viewModel = lifecycleVersion
        const val viewModelState = lifecycleVersion
    }

    object DI {
        const val hilt = "2.41"
        const val hiltCompiler = "1.0.0"
    }

    object Network {
        const val conscrypt = "2.5.1"
        const val gson = "2.8.7"
        const val retrofit = "2.9.0"
        const val paging = "3.1.1"
        const val rxJava3Adapter = retrofit
        const val gsonConverter = retrofit
        const val okHttp = "5.0.0-alpha.5"
        const val loggingInterceptor = okHttp
    }

    object Tools {
        const val timber = "5.0.1"
    }

    object Glide {
        const val core = "4.13.0"
    }

    object Test {
        const val junit = "4.13.2"
        const val androidJunit = "1.1.2"
        const val espressoCore = "3.3.0"
        const val truthExtVersion = "1.3.0-alpha01"
        const val mockKVersion = "1.9.3"
        const val coreTestingVersion = "2.0.0"
        const val hiltTesting = "2.28-alpha"
        const val hiltTestCompiler = hiltTesting
    }
}
