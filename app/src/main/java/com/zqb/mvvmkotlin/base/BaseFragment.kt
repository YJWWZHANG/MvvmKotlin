package com.zqb.mvvmkotlin.base

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

/**
 *创建时间:2019/4/25 10:39
 */
abstract class BaseFragment : AutoDisposeFragment(), KodeinAware {

    private val parentKodein by closestKodein()
    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        importModule(this)
    }

    open fun importModule(mainBuilder: Kodein.MainBuilder) {

    }

}