package com.zqb.mvvmkotlin.base

/**
 *创建时间:2019/4/25 09:58
 */
abstract class BaseActivity : SimpleActivity() {

    override fun onViewCreated() {
        super.onViewCreated()
        initInject()
    }

    abstract fun initInject()


}