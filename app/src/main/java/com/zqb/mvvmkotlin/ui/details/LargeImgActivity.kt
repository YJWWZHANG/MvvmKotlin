package com.zqb.mvvmkotlin.ui.details

import android.app.Activity
import android.content.Intent
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.base.SimpleActivity

/**
 *创建时间:2019/4/26 18:52
 */
class LargeImgActivity(override val layoutId: Int = R.layout.activity_large_img) : SimpleActivity() {

    companion object {
        fun launch(activity: Activity) {
            activity.startActivity(Intent(activity, LargeImgActivity::class.java))
        }
    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun onBackPressedSupport() {
        finish()
    }
}