package com.zqb.mvvmkotlin.base

import android.app.Activity
import com.zqb.mvvmkotlin.di.uiKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

/**
 *创建时间:2019/4/25 09:58
 */
abstract class BaseActivity : SimpleActivity(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
        bind<Activity>() with instance(this@BaseActivity)
        import(uiKodeinModule)
    }


    override fun onViewCreated() {
        super.onViewCreated()
        initInject()
    }

    abstract fun initInject()


}