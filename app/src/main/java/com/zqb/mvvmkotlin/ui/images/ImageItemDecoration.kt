package com.zqb.mvvmkotlin.ui.images

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v7.widget.StaggeredGridLayoutManager
import me.jessyan.autosize.utils.AutoSizeUtils


/**
 *创建时间:2019/4/18 17:51
 */
class ImageItemDecoration : RecyclerView.ItemDecoration() {

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    init {
        mPaint.color = Color.RED
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        AutoSizeUtils.dp2px(view.context, 10f)
        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        if (params.spanIndex % 2 == 0) {
            outRect.right = AutoSizeUtils.dp2px(view.context, 2f)
        } else {
            outRect.left = AutoSizeUtils.dp2px(view.context, 2f)
        }
        outRect.bottom = AutoSizeUtils.dp2px(view.context, 4f)
    }
}