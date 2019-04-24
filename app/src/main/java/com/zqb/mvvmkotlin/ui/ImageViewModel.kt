package com.zqb.mvvmkotlin.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.model.bean.ImageBean
import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.widgets.Loading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *创建时间:2019/4/23 20:03
 */
class ImageViewModel @Inject constructor(var mSougouApi: SougouApi, var loading: Loading) : ViewModel() {

    var liveData  = MutableLiveData<ImageBean>()

    private var mCurrentPage = 0
    private var mQuery = ""

    @SuppressLint("CheckResult")
    fun loadImage(position: Int){
        mQuery = Utils.getApp().resources.getStringArray(R.array.tab)[position]
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
//                loading.show()
            }
            .doFinally {
//                loading.dismiss()
            }
            .subscribe({
                LogUtils.e(it.items.toString())
                liveData.value = it
            }, {
                LogUtils.e(it.message)
            }, {

            })
    }

    @SuppressLint("CheckResult")
    fun loadMore() {
        mCurrentPage++
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {}
            .doFinally {}
            .subscribe({
                LogUtils.e(it.items.toString())
                liveData.value = it
            }, {
                LogUtils.e(it.message)
            }, {

            })

    }
}