package com.zqb.mvvmkotlin.ui.home.image

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.TAB_POSITION
import com.zqb.mvvmkotlin.base.DataBindingFragment
import com.zqb.mvvmkotlin.base.SimpleFragment
import com.zqb.mvvmkotlin.databinding.FragmentImageBinding
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
class ImageFragment : DataBindingFragment<FragmentImageBinding>() {

    @Inject
    lateinit var mImageViewModel: ImageViewModel
    private lateinit var mImageAdapter: ImageAdapter
    private var mIsShow = false

    override val layoutId: Int
        get() = R.layout.fragment_image

    override fun initInject() {
        DaggerFragmentComponent.builder()
            .activityComponent(
                DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(_mActivity))
                    .appComponent(DaggerAppComponent.builder().build())
                    .build())
            .build()
            .inject(this)
    }

    override fun bindingViewModel() {
        viewDataBinding.viewmodel = mImageViewModel
    }

    @SuppressLint("CheckResult")
    override fun initEventAndData() {
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(ImageItemDecoration())
        mImageAdapter = ImageAdapter(data = ArrayList())
        mImageAdapter.bindToRecyclerView(recycler_view)

        mImageAdapter.setOnLoadMoreListener({
            mImageViewModel.loadMore()
        }, recycler_view)

        mImageViewModel.liveData.observe(this,
                Observer<ImageBean> {
                    mImageAdapter.addData(it?.items ?: ArrayList())
                    mImageAdapter.loadMoreComplete()
                })

        mImageViewModel.loadImage(arguments?.getInt(TAB_POSITION) ?: 0)
    }

}