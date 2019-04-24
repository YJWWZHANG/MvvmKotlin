package com.zqb.mvvmkotlin.di.module

import android.app.Activity
import com.zqb.mvvmkotlin.di.scope.ActivityScope
import com.zqb.mvvmkotlin.widgets.Loading
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(var activity: Activity) {

    @Provides
    @ActivityScope
    fun providesActivity(): Activity = activity

    @Provides
    @ActivityScope
    fun providesLoading(): Loading = Loading(activity)

}
