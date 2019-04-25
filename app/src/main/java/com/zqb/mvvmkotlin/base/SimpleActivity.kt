package com.zqb.mvvmkotlin.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.noober.background.BackgroundLibrary
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class SimpleActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        onViewCreated()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            getContainer()?.setPadding(
                getContainer()?.paddingLeft ?: 0, (getContainer()?.paddingTop ?: 0) + BarUtils.getStatusBarHeight(),
                getContainer()?.paddingRight ?: 0, getContainer()?.paddingBottom ?: 0
            )
        }
        EventBus.getDefault().register(this)
        initEventAndData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    protected open fun onViewCreated() {
    }

    protected abstract val layoutId: Int
    abstract fun initEventAndData()

    protected open fun getContainer(): ViewGroup? {
        return null
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageNothingEvent(s: String) {

    }
}