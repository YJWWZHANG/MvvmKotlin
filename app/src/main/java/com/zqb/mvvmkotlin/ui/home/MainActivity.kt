package com.zqb.mvvmkotlin.ui.home

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.base.DataBindingActivity
import com.zqb.mvvmkotlin.databinding.ActivityMainBinding
import com.zqb.mvvmkotlin.di.component.DaggerActivityComponent
import com.zqb.mvvmkotlin.di.component.DaggerAppComponent
import com.zqb.mvvmkotlin.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_title_bar.*
import javax.inject.Inject

class MainActivity(override val layoutId: Int = R.layout.activity_main) : DataBindingActivity<ActivityMainBinding>() {

    @Inject
    lateinit var mMainViewModel: MainViewModel
    private var mExitTime = 0L

    override fun initInject() {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent(DaggerAppComponent.builder().build())
            .build()
            .inject(this)
    }

    override fun bindingViewModel() {
        viewDataBinding.viewmodel = mMainViewModel
    }

    override fun getContainer(): ViewGroup? {
        return title_bar
    }

    @SuppressLint("CheckResult")
    override fun initEventAndData() {
        view_pager.adapter = TabFragmentAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
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
