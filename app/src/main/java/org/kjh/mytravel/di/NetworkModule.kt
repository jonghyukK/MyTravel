package org.kjh.mytravel.di

import com.example.data.api.ApiService
import com.example.data.api.KakaoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * MyTravel
 * Class: NetworkModule
 * Created by mac on 2022/01/17.
 *
 * Description:
 */

private const val BASE_API_URL = "http://192.168.219.102:8080/"
private const val BASE_KAKAO_API_URL = "https://dapi.kakao.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoApiService(retrofit: Retrofit.Builder): KakaoApiService {
        return retrofit
            .baseUrl(BASE_KAKAO_API_URL)
            .build()
            .create(KakaoApiService::class.java)
    }
}