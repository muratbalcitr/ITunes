buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = java.net.URI.create("https://maven.fabric.io/public")
            url = java.net.URI.create("https://plugins.gradle.org/m2/")
            url = java.net.URI.create("https://jitpack.io")
        }
    }

    dependencies {
        classpath(Classpaths.gradleClasspath)
        classpath(Classpaths.kotlinGradleClasspath)
        classpath(Classpaths.kotlinSerialization)
        classpath(Classpaths.safeVarargs)
        classpath(Classpaths.hiltGradlePlugin)
        classpath(Classpaths.googleServices)
        classpath(Classpaths.crashlytics)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = java.net.URI.create("http://maven.google.com/")
            url = java.net.URI.create("https://jitpack.io")

            isAllowInsecureProtocol = true
        }
    }
}
tasks.register("clean").configure {
    delete("build")
}
