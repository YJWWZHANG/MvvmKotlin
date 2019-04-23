package com.zqb.mvvmkotlin

import android.view.ViewGroup
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvpkotlin.ui.image.TabFragmentAdapter
import com.zqb.mvvmkotlin.base.SimpleActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_title_bar.*

class MainActivity(override val layoutId: Int = R.layout.activity_main) : SimpleActivity() {

    private var mExitTime = 0L

    override fun getContainer(): ViewGroup? {
        return title_bar
    }

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
