package com.architecture.data.remote

import com.architecture.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


object ApiClient {

    private var retrofit: Retrofit? = null

    @Singleton
    val client : Retrofit?
        get() {
            if (retrofit == null) {

//                val certificatePinner = CertificatePinner.Builder()
//                    .add("api.github.com", "sha256/6wJsqVDF8K19zxfLxV5DGRneLyzso9adVdUN/exDacw=")
//                    .build()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
//                    .certificatePinner(certificatePinner)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl("http://www.mocky.io/v2/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit
    }
}