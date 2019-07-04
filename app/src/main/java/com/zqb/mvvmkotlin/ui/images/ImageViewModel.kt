package com.zqb.mvvmkotlin.ui.images

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.uber.autodispose.autoDisposable
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.REFRESH
import com.zqb.mvvmkotlin.base.BaseViewModel
import com.zqb.mvvmkotlin.model.bean.ImageBean
import com.zqb.mvvmkotlin.model.bean.Resource
import com.zqb.mvvmkotlin.model.net.SougouApi
import com.zqb.mvvmkotlin.utils.SingletonHolderSingleArg
import com.zqb.mvvmkotlin.widgets.Loading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *创建时间:2019/4/23 20:03
 */
class ImageViewModel constructor(var mSougouApi: SougouApi, var loading: Loading) : BaseViewModel() {

    var liveData  = MutableLiveData<Resource<ArrayList<ImageBean.Item>>>()

    private var mCurrentPage = 0
    private var mQuery = ""

    fun loadImage(position: Int){
        mQuery = Utils.getApp().resources.getStringArray(R.array.tab)[position]
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.show()
            }
            .doFinally {
                loading.dismiss()
            }
            .autoDisposable(this)
            .subscribe({
                liveData.value = Resource.success(it.items)
            }, {
                liveData.value = Resource.error(it.message)
            }, {

            })
    }

    fun loadMore() {
        mCurrentPage += 48
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe({
                liveData.value = Resource.success(it.items)
            }, {
                liveData.value = Resource.error(it.message)
            }, {

            })

    }

    fun refresh() {
        mCurrentPage = 0
        mSougouApi.loadImage(mQuery, mCurrentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe({
                liveData.value = Resource.success(it.items, REFRESH)
            }, {
                liveData.value = Resource.error(it.message)
            }, {

            })
    }
}

@Suppress("UNCHECKED_CAST")
class ImageViewModelFactory(
    private val repo: SougouApi, private val loading: Loading
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ImageViewModel(repo, loading) as T

    companion object : SingletonHolderSingleArg<ImageViewModelFactory, SougouApi, Loading>(::ImageViewModelFactory)
}