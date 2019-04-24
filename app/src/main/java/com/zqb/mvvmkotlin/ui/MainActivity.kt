package com.zqb.mvvmkotlin.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.base.SimpleActivity
import com.zqb.mvvmkotlin.di.component.DaggerActivityComponent
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.module.ActivityModule
import com.zqb.mvvmkotlin.model.net.SougouApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_title_bar.*
import javax.inject.Inject

class MainActivity(override val layoutId: Int = R.layout.activity_main) : SimpleActivity() {

    private var mExitTime = 0L

    @Inject
    lateinit var mSougouApi: SougouApi

    override fun getContainer(): ViewGroup? {
        return title_bar
    }

    @SuppressLint("CheckResult")
    override fun initEventAndData() {
        view_pager.adapter = TabFragmentAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent(DaggerAppComponent.builder().build())
            .build()
            .inject(this)
        mSougouApi.loadImage("动漫", 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtils.e(it.toString())
            }, {
                ToastUtils.showShort(it.message)
            }, {

            })
    }

    override fun onBackPressedSupport() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis()
            ToastUtils.showShort("再按一次退出程序")
        } else {
            AppUtils.exitApp()
        }

    }

}
