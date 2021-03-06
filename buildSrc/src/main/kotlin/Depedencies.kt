package com.architecture

object Dependencies {

    const val kotlinVersion = "1.4.30"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val viewBindingHelper =
        "com.github.CraZyLegenD.Set-Of-Useful-Kotlin-Extensions-and-Helpers:viewbinding:2.1.15"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Android {
        const val minSdkVersion = 23
        const val targetSdkVersion = 30
        const val compileSdkVersion = 30
        const val applicationId = "com.architecture"
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object BuildPlugins {

        private const val buildToolsVersion = "4.1.2"

        const val androidGradlePlugin = "com.android.tools.build:gradle:$buildToolsVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val googleServicesGradlePlugin = "com.google.gms:google-services:4.3.3"
        const val googleServicesPlugin = "com.google.gms.google-services"

        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
        const val kotlinParcelize = "kotlin-parcelize"
        const val kapt = "kotlin-kapt"

        const val androidLibrary = "com.android.library"
        const val versionGradlePlugin = "com.palantir.git-version:0.12.3"
        const val versionPlugin = "com.palantir.git-version"
        const val safeArgsPlugin = "androidx.navigation.safeargs.kotlin"
        const val crashlyticsPlugin =  "com.google.firebase.crashlytics"

    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val savedState = "androidx.savedstate:savedstate:1.1.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:2.0.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val viewPager = "androidx.viewpager2:viewpager2:1.0.0"
        const val preference = "androidx.preference:preference-ktx:1.1.1"
        const val security = "androidx.security:security-crypto:1.0.0-beta01"

        object Activity {
            private const val version = "1.2.0"
            const val activity = "androidx.activity:activity:$version"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.3.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
            const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
            const val process = "androidx.lifecycle:lifecycle-process:$version"
        }

        object Navigation {
            private const val version = "2.3.3"
            const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val navigationUi = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Room {
            private const val version = "2.2.6"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

        object Work {
            private const val version = "2.5.0"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val gcm = "androidx.work:work-gcm:$version"
            const val testing = "androidx.work:work-testing:$version"
        }

    }

    object Koin {
        private const val version = "2.2.2"
        const val fragment = "org.koin:koin-androidx-fragment:$version"
        const val scope = "org.koin:koin-androidx-scope:$version"
        const val viewModel = "org.koin:koin-androidx-viewmodel:$version"
        const val androidxExt = "org.koin:koin-androidx-ext:$version"
        const val gradlePlugin = "org.koin:koin-gradle-plugin:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava3:$version"
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Rx {
        private const val version = "3.0.0"
        const val rxJava = "io.reactivex.rxjava3:rxjava:$version"
        const val rxAndroid = "io.reactivex.rxjava3:rxandroid:$version"
    }

}