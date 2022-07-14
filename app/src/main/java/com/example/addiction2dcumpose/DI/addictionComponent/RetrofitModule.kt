package com.example.addiction2dcumpose.DI.addictionComponent

import com.example.addiction2dcumpose.Constants
import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class RetrofitModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): RetrofitService {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

        return retrofit.create(RetrofitService::class.java)
    }
}