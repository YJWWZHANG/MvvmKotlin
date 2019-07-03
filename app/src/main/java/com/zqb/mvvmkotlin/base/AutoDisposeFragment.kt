package com.zqb.mvvmkotlin.base

import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.zqb.mvvmkotlin.base.SimpleFragment

abstract class AutoDisposeFragment : SimpleFragment() {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
    }
}