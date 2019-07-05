package com.zqb.mvvmkotlin.model.net

import com.zqb.mvvmkotlin.model.bean.ImageData
import com.zqb.mvvmkotlin.utils.RxSchedulers
import com.zqb.mvvmkotlin.widgets.Loading
import io.reactivex.Flowable

class SougouRepository constructor(var mSougouApi: SougouApi, var loading: Loading) {

    fun loadImage(query: String, start: Int, isShowLoadIng: Boolean = true): Flowable<ImageData> {
        return if (isShowLoadIng) {
            mSougouApi.loadImage(query, start)
                .subscribeOn(RxSchedulers.io)
                .observeOn(RxSchedulers.ui)
                .doOnSubscribe {
                    loading.show()
                }
                .doFinally {
                    loading.dismiss()
                }
        } else {
            mSougouApi.loadImage(query, start)
                .subscribeOn(RxSchedulers.io)
                .observeOn(RxSchedulers.ui)
        }
    }

}