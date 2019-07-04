package com.zqb.mvvmkotlin.di

import android.app.Application
import com.blankj.utilcode.util.NetworkUtils
import com.readystatesoftware.chuck.ChuckInterceptor
import com.zqb.mvvmkotlin.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val NET_MODULE_TAG = "NET_MODULE_TAG"

const val TIME_OUT_SECONDS = 10
const val BASE_URL = "http://pic.sogou.com"

const val INTERCEPTOR_LOG_TAG = "INTERCEPTOR_LOG_TAG"
const val INTERCEPTOR_CHUCK_TAG = "INTERCEPTOR_CHUCK_TAG"

val netKodeinModule = Kodein.Module(NET_MODULE_TAG) {

    bind<Interceptor>(INTERCEPTOR_LOG_TAG) with singleton {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    bind<Interceptor>(INTERCEPTOR_CHUCK_TAG) with singleton {
        ChuckInterceptor(instance())
    }

    bind<Cache>() with singleton {
        Cache(File(instance<Application>().cacheDir, "NetDataCache"), 10 * 1024 * 1024)
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(instance(INTERCEPTOR_LOG_TAG))
                addInterceptor(instance(INTERCEPTOR_CHUCK_TAG))
            }
            connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            addNetworkInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val onlineCacheTime = 30    //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                return@addNetworkInterceptor response.newBuilder()
                    .header("Cache-Control", "public, max-age=$onlineCacheTime")
                    .removeHeader("Pragma")
                    .build()
            }
            addInterceptor { chain ->
                var request = chain.request()
                if (!NetworkUtils.isConnected()) {
                    val offlineCacheTime = 60//离线的时候的缓存的过期时间
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                        .build()
                }
                return@addInterceptor chain.proceed(request)
            }
            cache(instance())
        }.build()
    }

    bind<Retrofit>() with singleton {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
            client(instance())
        }.build()
    }
}