package com.zqb.mvvmkotlin.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.zqb.mvpkotlin.ui.image.ImageItemDecoration
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.TAB_POSITION
import com.zqb.mvvmkotlin.base.SimpleFragment
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.component.DaggerFragmentComponent
import com.zqb.mvvmkotlin.model.bean.ImageBean
import com.zqb.mvvmkotlin.model.net.SougouApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_image.*
import javax.inject.Inject

/**
 *创建时间:2019/4/17 16:24
 */
class ImageFragment : SimpleFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_image

    private lateinit var mImageAdapter: ImageAdapter

    @Inject
    lateinit var mImageViewModel: ImageViewModel

    @SuppressLint("CheckResult")
    override fun initEventAndData() {
        DaggerFragmentComponent.builder()
            .appComponent(DaggerAppComponent.builder().build())
            .build()
            .inject(this)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(ImageItemDecoration())
        mImageAdapter = ImageAdapter(data = ArrayList())
        mImageAdapter.bindToRecyclerView(recycler_view)

        mImageViewModel.loadImage(Utils.getApp().resources.getStringArray(R.array.tab)[arguments?.getInt(TAB_POSITION) ?: 0], 0)
            .observe(this,
                Observer<ImageBean> {
                    mImageAdapter.addData(it?.items ?: ArrayList())
                })

    }

}