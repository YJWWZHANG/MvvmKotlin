package com.zqb.mvvmkotlin.di

import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.ui.images.ImageViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

private const val IMAGE_MODULE_TAG = "IMAGE_MODULE_TAG"

val imageKodeinModule = Kodein.Module(IMAGE_MODULE_TAG) {

    bind<SougouApi>() with singleton {
        instance<Retrofit>().create(SougouApi::class.java)
    }

    bind<ImageViewModel>() with singleton {
        ImageViewModel(instance(), instance())
    }
}