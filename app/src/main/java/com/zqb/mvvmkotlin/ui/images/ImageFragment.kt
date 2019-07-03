package com.zqb.mvvmkotlin.ui.images

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.SuperKotlin.pictureviewer.ImagePagerActivity
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.app.REFRESH
import com.zqb.mvvmkotlin.app.TAB_POSITION
import com.zqb.mvvmkotlin.base.BaseFragment
import com.zqb.mvvmkotlin.di.component.DaggerActivityComponent
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.component.DaggerFragmentComponent
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.model.enum.Status
import kotlinx.android.synthetic.main.fragment_image.*
import javax.inject.Inject
import com.SuperKotlin.pictureviewer.PictureConfig
import com.zqb.mvvmkotlin.R


/**
 *创建时间:2019/4/17 16:24
 */
class ImageFragment : BaseFragment() {

    @Inject
    lateinit var mImageViewModel: ImageViewModel
    private lateinit var mImageAdapter: ImageAdapter

    private var mImages = arrayListOf<String>()

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

    override fun initView() {
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(ImageItemDecoration())
        mImageAdapter = ImageAdapter(data = ArrayList())
        mImageAdapter.bindToRecyclerView(recycler_view)
    }

    override fun initEvent() {
        mImageAdapter.setOnItemClickListener { adapter, view, position ->
            //使用方式
            val config = PictureConfig.Builder()
                .setListData(mImages)    //图片数据List<String> list
                .setPosition(position)    //图片下标（从第position张图片开始浏览）
                .setDownloadPath("MvvmKotlin")    //图片下载文件夹地址
                .setIsShowNumber(true)//是否显示数字下标
                .needDownload(true)    //是否支持图片下载
                .setPlacrHolder(R.mipmap.ic_loading)    //占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                .build()
            ImagePagerActivity.startActivity(_mActivity, config)
//            LargeImgActivity.launch(_mActivity)
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
                            mImages.clear()
                        } else {
                            mImageAdapter.addData(it.data ?: ArrayList())
                            mImageAdapter.loadMoreComplete()
                        }
                        it.data?.forEach { item ->
                            mImages.add(item.pic_url)
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

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mImageViewModel.loadImage(arguments?.getInt(TAB_POSITION) ?: 0)
    }

}