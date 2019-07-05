package com.zqb.mvvmkotlin.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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
        ViewModelProviders
            .of(context as Fragment, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ImageViewModel(instance(), instance()) as T
                }
            })
            .get(ImageViewModel::class.java)
    }
}