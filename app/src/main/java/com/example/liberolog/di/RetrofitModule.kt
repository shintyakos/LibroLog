package com.example.liberolog.di

import android.util.Log
import com.example.liberolog.api.GoogleBookSearchService
import com.example.liberolog.utils.GOOGLE_BOOK_URL
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

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGoogleBookSearchService(retrofit: Retrofit): GoogleBookSearchService {
        return retrofit.create(GoogleBookSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideGoogleBookSearchRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_BOOK_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor {
                // Timber.tag("OkHttp").d(it)
                Log.d("http-log", "OkHttp: $it")
            }
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(
                Interceptor { chain ->
                    val request = chain.request()
                    val response: Response = chain.proceed(request)
                    if (response.code == 500) {
                        Log.e("http-log", "500 error")
                    }
                    return@Interceptor response
                },
            )
            .build()
    }
}
