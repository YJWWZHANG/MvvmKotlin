package com.zqb.mvvmkotlin.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.squareup.leakcanary.LeakCanary
import com.zqb.mvvmkotlin.BuildConfig
import com.zqb.mvvmkotlin.di.netKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 *创建时间:2019/4/23 16:10
 */
class App: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind<Application>() with singleton { this@App }
        import(netKodeinModule)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        LogUtils.getConfig().apply {
            if (!BuildConfig.DEBUG) {
                isLogSwitch = false
            }
            setBorderSwitch(false)
        }
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this@App)
        }
    }
}