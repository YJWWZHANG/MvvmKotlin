package com.zqb.mvvmkotlin.ui.images

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.zqb.mvvmkotlin.model.net.SougouApi
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
            .of(context as Fragment, ImageViewModelFactory.getInstance(instance(), instance()))
            .get(ImageViewModel::class.java)
    }
}