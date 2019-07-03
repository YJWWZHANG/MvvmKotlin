package com.zqb.mvvmkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *创建时间:2019/4/25 10:39
 */
abstract class BaseFragment: SimpleFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initInject()

}