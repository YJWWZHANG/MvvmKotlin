package com.zqb.mvvmkotlin.di.component

import com.zqb.mvvmkotlin.di.module.AppModule
import com.zqb.mvvmkotlin.di.scope.AppScope
import com.zqb.mvvmkotlin.model.net.SougouApi
import dagger.Component
import javax.inject.Singleton

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    val sougouApi: SougouApi
}