package com.zqb.mvvmkotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *创建时间:2019/4/25 10:39
 */
abstract class DataBindingFragment<T : ViewDataBinding> : SimpleFragment() {

    lateinit var viewDataBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        bindingViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initInject()

    abstract fun bindingViewModel()
}