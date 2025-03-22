package com.newapp.composeapplicationstart.di

import android.util.Log
import com.newapp.composeapplicationstart.BuildConfig
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.presentation.utils.EncryptedSharedPrefKeyValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideTMDBApiClass(retrofit: Retrofit): TMBDApiInterface {
        return retrofit.create(TMBDApiInterface::class.java)
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPrefKeyValue: EncryptedSharedPrefKeyValue): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("API_LOG", message)
        }
        loggingInterceptor.level = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY // Log request and response body
        } else{
             HttpLoggingInterceptor.Level.NONE
        }

        val authLoggingInterceptor = Interceptor { chain ->
            val accessToken = sharedPrefKeyValue.getAccessToken()
            Log.d("*AccessToken","$accessToken")
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .addHeader("Accept","application/json")
                .build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authLoggingInterceptor)
            .build()
    }



}