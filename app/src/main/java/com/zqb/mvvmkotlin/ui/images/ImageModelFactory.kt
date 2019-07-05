package com.zqb.mvvmkotlin.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zqb.mvvmkotlin.model.net.SougouRepository

class ImageModelFactory(private val repository: SougouRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(repository) as T
    }
}