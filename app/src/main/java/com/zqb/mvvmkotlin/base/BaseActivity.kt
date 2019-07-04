package com.zqb.mvvmkotlin.base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.zqb.mvvmkotlin.di.uiKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

/**
 *创建时间:2019/4/25 09:58
 */
abstract class BaseActivity : AutoDisposeActivity(), KodeinAware {

    private val parentKodein by closestKodein()
    override val kodeinContext = kcontext<AppCompatActivity>(this)

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
        bind<Activity>() with instance(this@BaseActivity)
        import(uiKodeinModule)
        importModule(this)
    }

    open fun importModule(mainBuilder: Kodein.MainBuilder) {

    }

}