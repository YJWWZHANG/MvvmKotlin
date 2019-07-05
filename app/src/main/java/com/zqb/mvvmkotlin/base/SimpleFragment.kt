package com.zqb.mvvmkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class SimpleFragment : SupportFragment() {

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initDataAndView()
        initEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    protected abstract fun initDataAndView()
    protected abstract fun initEvent()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageNothingEvent(s: String) {

    }
}
