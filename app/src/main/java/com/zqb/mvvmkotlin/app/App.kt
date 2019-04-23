package com.zqb.mvvmkotlin.app

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 *创建时间:2019/4/23 16:10
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}