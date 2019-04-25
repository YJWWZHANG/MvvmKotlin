package com.zqb.mvvmkotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

/**
 *创建时间:2019/4/25 09:58
 */
abstract class DataBindingActivity<T : ViewDataBinding> : SimpleActivity() {

    lateinit var viewDataBinding: T

    override fun onViewCreated() {
        super.onViewCreated()
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        initInject()
        bindingViewModel()
    }

    abstract fun initInject()

    abstract fun bindingViewModel()

}