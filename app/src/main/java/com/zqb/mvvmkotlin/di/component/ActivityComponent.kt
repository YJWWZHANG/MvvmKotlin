package com.zqb.mvvmkotlin.di.component

import com.zqb.mvvmkotlin.ui.MainActivity
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}
