package com.zqb.mvvmkotlin.ui.images

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.Utils
import com.uber.autodispose.autoDisposable
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.base.BaseViewModel
import com.zqb.mvvmkotlin.model.bean.ImageData
import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.utils.RxSchedulers
import com.zqb.mvvmkotlin.widgets.Loading

/**
 *创建时间:2019/4/23 20:03
 */
class ImageViewModel constructor(var mSougouApi: SougouApi, var loading: Loading) : BaseViewModel() {

    var liveLoad  = MutableLiveData<ArrayList<ImageData.Item>>()
    var liveLoadMore  = MutableLiveData<ArrayList<ImageData.Item>>()
    var liveError  = MutableLiveData<Throwable>()

    private var mCurrentPage = 0
    private var mQuery = ""

    fun load(position: Int){
        mQuery = Utils.getApp().resources.getStringArray(R.array.tab)[position]
        mCurrentPage = 0
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(RxSchedulers.io)
            .observeOn(RxSchedulers.ui)
            .doOnSubscribe {
                loading.show()
            }
            .doFinally {
                loading.dismiss()
            }
            .autoDisposable(this)
            .subscribe({
                liveLoad.value = it.items
            }, {
                liveError.value = it
            })
    }

    fun loadMore() {
        mCurrentPage += 48
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(RxSchedulers.io)
            .observeOn(RxSchedulers.ui)
            .autoDisposable(this)
            .subscribe({
                liveLoadMore.value = it.items
            }, {
                liveError.value = it
            })

    }

}

