package com.zqb.mvvmkotlin.widgets

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.zqb.mvvmkotlin.R

class Loading @JvmOverloads constructor(context: Context, themeResId: Int = R.style.BaseDialog) :
    Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_load)
        setCanceledOnTouchOutside(false)
    }
}
