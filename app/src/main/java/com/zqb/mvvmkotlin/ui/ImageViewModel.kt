package com.zqb.mvvmkotlin.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.zqb.mvvmkotlin.model.bean.ImageBean
import com.zqb.mvvmkotlin.model.net.SougouApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *创建时间:2019/4/23 20:03
 */
class ImageViewModel @Inject constructor(var mSougouApi: SougouApi) : ViewModel() {

    private var mImageLiveData: MutableLiveData<ImageBean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun loadImage(query: String, start: Int) : LiveData<ImageBean>{
        mSougouApi.loadImage(query, start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {

            }
            .doFinally {

            }
            .subscribe({
                LogUtils.e(it.items.toString())
                mImageLiveData.value = it
            }, {
                LogUtils.e(it.message)
            }, {

            })
        return mImageLiveData
    }
}