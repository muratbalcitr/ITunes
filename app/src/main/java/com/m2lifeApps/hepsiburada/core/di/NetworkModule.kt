package com.m2lifeApps.hepsiburada.core.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.m2lifeApps.data.remote.ProjectService
import com.m2lifeApps.hepsiburada.BuildConfig
import com.m2lifeApps.hepsiburada.core.di.qualifers.DefaultOkHttpClientBuilder
import com.m2lifeApps.hepsiburada.core.di.qualifers.ProjectOkHttpClient
import com.m2lifeApps.hepsiburada.core.di.qualifers.ProjectRetrofit
import com.m2lifeApps.hepsiburada.core.netwok.DEFAULT_CALL_TIMEOUT_MILLIS
import com.m2lifeApps.hepsiburada.core.netwok.DEFAULT_CONNECT_TIMEOUT_MILLIS
import com.m2lifeApps.hepsiburada.core.netwok.DEFAULT_READ_TIMEOUT_MILLIS
import com.m2lifeApps.hepsiburada.core.netwok.DEFAULT_WRITE_TIMEOUT_MILLIS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideProjectService(
        @ProjectRetrofit projectRetrofit: Retrofit
    ): ProjectService = projectRetrofit.create(ProjectService::class.java)

    @ProjectRetrofit
    @Provides
    fun provideProjectRetrofit(
        @ProjectOkHttpClient projectOkHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        baseUrl(BuildConfig.SERVICE_URL)
        addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        addConverterFactory(GsonConverterFactory.create(gson))
        client(projectOkHttpClient)
    }.build()

    @ProjectOkHttpClient
    @Provides
    fun provideProjectOkHttpClient(
        @DefaultOkHttpClientBuilder okHttpClientBuilder: OkHttpClient.Builder,
        @ApplicationContext context: Context
    ) = okHttpClientBuilder.apply {
        this.addInterceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")

            chain.proceed(builder.build())
        }
    }.build()

    @Provides
    @DefaultOkHttpClientBuilder
    fun provideDefaultOkHttpBuilder(
        @ApplicationContext context: Context
    ): OkHttpClient.Builder = OkHttpClient.Builder()
        .cache(cache(context))
        .addInterceptor(provideLoggingInterceptor())
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")

            chain.proceed(builder.build())
        }
        .addNetworkInterceptor(provideCacheInterceptor())
        .addInterceptor(provideOfflineCacheInterceptor())
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
        .callTimeout(DEFAULT_CALL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)

    fun cache(context: Context): Cache {

        val httpCacheDirectory = File(context.cacheDir, "offlineCache")

        return Cache(httpCacheDirectory, 20 * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)
            val cacheControl: String? = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                    "no-cache"
                ) ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
            ) {
                val cc: CacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)
            } else {
                originalResponse
            }
        }
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl: CacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                val offlineRequest: Request = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                chain.proceed(offlineRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    /** Provides special OkHttp client for ONLY refresh token request **/
    @Provides
    fun provideClient(
        refreshToken: String
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(builder)
            }.addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            .build()
    }
}
