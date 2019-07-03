package com.zqb.mvvmkotlin.base

import android.os.Bundle
import android.view.View
import com.zqb.mvvmkotlin.di.imageKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 *创建时间:2019/4/25 10:39
 */
abstract class BaseFragment : SimpleFragment(), KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
//        bind<Activity>() with instance(_mActivity)
//        import(uiKodeinModule)
        import(imageKodeinModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initInject()

}