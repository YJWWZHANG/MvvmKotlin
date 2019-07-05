package com.zqb.mvvmkotlin.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.widgets.Loading

class ImageModelFactory(private val repository: SougouApi, private val loading: Loading) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(repository, loading) as T
    }
}