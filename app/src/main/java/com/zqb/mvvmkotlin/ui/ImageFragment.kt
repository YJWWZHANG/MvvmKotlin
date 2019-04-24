package com.zqb.mvvmkotlin.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.zqb.mvpkotlin.ui.image.ImageItemDecoration
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.TAB_POSITION
import com.zqb.mvvmkotlin.base.SimpleFragment
import com.zqb.mvvmkotlin.di.component.DaggerActivityComponent
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.component.DaggerFragmentComponent
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.model.bean.ImageBean
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
            .activityComponent(
                DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(_mActivity))
                    .appComponent(DaggerAppComponent.builder().build())
                    .build())
            .build()
            .inject(this)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(ImageItemDecoration())
        mImageAdapter = ImageAdapter(data = ArrayList())
        mImageAdapter.bindToRecyclerView(recycler_view)

        mImageAdapter.setOnLoadMoreListener({
            mImageViewModel.loadMore()
        }, recycler_view)

        mImageViewModel.loadImage(arguments?.getInt(TAB_POSITION) ?: 0)

        mImageViewModel.liveData.observe(this,
                Observer<ImageBean> {
                    mImageAdapter.addData(it?.items ?: ArrayList())
                    mImageAdapter.loadMoreComplete()
                })

    }

}