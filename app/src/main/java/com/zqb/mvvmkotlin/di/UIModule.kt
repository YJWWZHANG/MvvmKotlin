package com.zqb.mvvmkotlin.di

import android.app.Activity
import com.zqb.mvvmkotlin.widgets.Loading
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

private const val UI_MODULE_TAG = "UI_MODULE_TAG"

val uiKodeinModule = Kodein.Module(UI_MODULE_TAG) {

    bind<Loading>() with singleton {
        Loading(instance<Activity>())
    }
}