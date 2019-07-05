package com.zqb.mvvmkotlin.ui.images

import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.model.bean.ImageData
import kotlinx.android.synthetic.main.item_image.view.*
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 *创建时间:2019/4/17 18:33
 */
class ImageAdapter(layoutResId: Int = R.layout.item_image, data: List<ImageData.Item>) :
    BaseQuickAdapter<ImageData.Item, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ImageData.Item) {
//        LogUtils.e(item.toString())
        helper.itemView.iv_pic.layoutParams.height = (item.height.toFloat() / item.width.toFloat() * (ScreenUtils.getScreenWidth() / 2 - AutoSizeUtils.dp2px(helper.itemView.context, 2f))).toInt()
        Glide.with(helper.itemView.context)
            .load(item.thumbUrl)
            .placeholder(R.mipmap.ic_loading)
            .into(helper.itemView.iv_pic)
    }
}