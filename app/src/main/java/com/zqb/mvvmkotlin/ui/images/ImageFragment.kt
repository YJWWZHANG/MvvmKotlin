package com.zqb.mvvmkotlin.ui.images

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.REFRESH
import com.zqb.mvvmkotlin.app.TAB_POSITION
import com.zqb.mvvmkotlin.base.DataBindingFragment
import com.zqb.mvvmkotlin.databinding.FragmentImageBinding
import com.zqb.mvvmkotlin.di.component.DaggerActivityComponent
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.component.DaggerFragmentComponent
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.model.enum.Status
import com.zqb.mvvmkotlin.ui.details.LargeImgActivity
import kotlinx.android.synthetic.main.fragment_image.*
import javax.inject.Inject

/**
 *创建时间:2019/4/17 16:24
 */
class ImageFragment : DataBindingFragment<FragmentImageBinding>() {

    @Inject
    lateinit var mImageViewModel: ImageViewModel
    private lateinit var mImageAdapter: ImageAdapter

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

    override fun initView() {
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(ImageItemDecoration())
        mImageAdapter = ImageAdapter(data = ArrayList())
        mImageAdapter.bindToRecyclerView(recycler_view)

        mImageViewModel.loadImage(arguments?.getInt(TAB_POSITION) ?: 0)
    }

    override fun initEvent() {
        mImageAdapter.setOnItemClickListener { adapter, view, position ->
            LargeImgActivity.launch(_mActivity)
        }
        mImageAdapter.setOnLoadMoreListener({
            mImageViewModel.loadMore()
        }, recycler_view)
        swipe_refresh.setOnRefreshListener {
            mImageViewModel.refresh()
        }
        mImageViewModel.liveData.observe(this,
            Observer {
                when(it?.status) {
                    Status.SUCCESS -> {
                        swipe_refresh.isRefreshing = false
                        if (it.message == REFRESH) {
                            mImageAdapter.setNewData(it.data)
                        } else {
                            mImageAdapter.addData(it.data ?: ArrayList())
                            mImageAdapter.loadMoreComplete()
                        }
                    }
                    Status.ERROR -> {
                        swipe_refresh.isRefreshing = false
                        mImageAdapter.loadMoreFail()
                        ToastUtils.showShort(it.message)
                    }
                    Status.LOADING -> {}
                }
            })
    }

}