package com.zqb.mvvmkotlin.di.component

import com.zqb.mvvmkotlin.ui.home.MainActivity
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.di.scope.ActivityScope
import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.widgets.Loading
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    val sougouApi: SougouApi
    val loading: Loading

    fun inject(mainActivity: MainActivity)

}
