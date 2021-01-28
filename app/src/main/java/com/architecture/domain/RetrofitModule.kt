package com.architecture.domain

import com.architecture.BuildConfig
import com.architecture.app.AppExecutors
import com.architecture.data.remote.Network
import com.architecture.utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single<Network> {
        getNetwork(get())
    }

    single<Retrofit> {
        getRetrofit(get())
    }

    single<OkHttpClient> {
        getOkHttpClient(get())
    }

    single<HttpLoggingInterceptor> {
        getHttpLoggingInterceptor()
    }

    single {
        AppExecutors()
    }
}

private fun getNetwork(retroFit: Retrofit): Network {

    return retroFit.create(Network::class.java)
}

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder()
//            .baseUrl("http://www.mocky.io/v2/")
        .baseUrl(BuildConfig.BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create())
//        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return httpLoggingInterceptor
}

/*
@Module
class RetrofitModule internal constructor() {

    @Provides
    @Singleton
    fun getNetwork(retroFit: Retrofit): Network {
        return retroFit.create(Network::class.java)
    }

    @Provides
    @Singleton
    fun getAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("http://www.mocky.io/v2/")
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

}*/
