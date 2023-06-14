package com.bancom.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    companion object {
        const val CONNECT_TIMEOUT: Long = 60 * 1000
        const val READ_TIMEOUT: Long = 60 * 1000
        const val WRITE_TIMEOUT: Long = 60 * 1000
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
    }

    @Provides
    @Singleton
    internal fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        okHttpClient.addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        //okHttpClient.addInterceptor(ChuckerInterceptor(context))
        return okHttpClient.build()
    }
}
