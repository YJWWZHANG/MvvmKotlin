package com.zqb.mvvmkotlin.ui.home

import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_title_bar.*

class MainActivity(override val layoutId: Int = R.layout.activity_main) : BaseActivity() {

    private var mExitTime = 0L

    override fun getContainer(): ViewGroup? {
        return title_bar
    }

    override fun initView() {
        view_pager.adapter = TabFragmentAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initEvent() {

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
