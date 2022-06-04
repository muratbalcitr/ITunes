object Flavors {
    object ProductFlavors {
        const val DEV = "dev"
        const val STORE = "store"
    }

    object FlavorDimensions {
        const val ENVIRONMENT = "enviroment"
        const val DISTRIBUTE = "distribute"
    }

    object BuildTypes {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    object Default {
        const val MAIN = "main"
        const val BUILD_TYPE = BuildTypes.DEBUG
        const val BUILD_FLAVOR = ProductFlavors.DEV

        val BUILD_VARIANT = "${BUILD_FLAVOR.capitalize()}${BUILD_TYPE.capitalize()}"
    }
}
