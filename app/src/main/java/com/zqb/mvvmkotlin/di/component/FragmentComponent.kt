package com.zqb.mvvmkotlin.di.component

import com.zqb.mvvmkotlin.ui.home.image.ImageFragment
import com.zqb.mvvmkotlin.di.module.FragmentModule
import com.zqb.mvvmkotlin.di.scope.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(imageFragment: ImageFragment)
}