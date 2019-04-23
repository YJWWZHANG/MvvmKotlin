package com.zqb.mvvmkotlin.di.module

import android.annotation.SuppressLint
import android.app.Application
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.Utils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.zqb.mvvmkotlin.di.scope.AppScope
import com.zqb.mvvmkotlin.model.net.SougouApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *创建时间:2019/4/19 17:15
 */
@Module
class AppModule {

    companion object {
        const val BASE_URL = "http://pic.sogou.com"
        const val HTTP_CONNECT_TIMEOUT = 60 * 1000L
        const val HTTP_READ_TIMEOUT = 60 * 1000L
    }

    @Provides
    @AppScope
    fun providesApplication(): Application {
        return Utils.getApp()
    }

    @Provides
    @AppScope
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @AppScope
    fun providesChuckInterceptor(application: Application): ChuckInterceptor {
        return ChuckInterceptor(application)
    }

    @Provides
    @AppScope
    fun providesCache(application: Application): Cache {
        return Cache(File(application.cacheDir, "NetDataCache"), 10 * 1024 * 1024)
    }

    @Provides
    @AppScope
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .addNetworkInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val response = chain.proceed(request)
                    val onlineCacheTime = 30    //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                    return response.newBuilder()
                        .header("Cache-Control", "public, max-age=$onlineCacheTime")
                        .removeHeader("Pragma")
                        .build()
                }
            })
            .addInterceptor(object : Interceptor {
                @SuppressLint("MissingPermission")
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    if (!NetworkUtils.isConnected()) {
                        val offlineCacheTime = 60//离线的时候的缓存的过期时间
                        request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                            .build()
                    }
                    return chain.proceed(request)
                }
            })
            .cache(cache)
            .build()
    }

    @Provides
    @AppScope
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @AppScope
    fun providesImageApi(retrofit: Retrofit): SougouApi {
        return retrofit.create(SougouApi::class.java)
    }

}