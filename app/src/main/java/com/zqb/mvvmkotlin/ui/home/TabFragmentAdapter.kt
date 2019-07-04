package com.zqb.mvvmkotlin.ui.home

import com.blankj.utilcode.util.Utils
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zqb.mvvmkotlin.ui.images.ImageFragment
import com.zqb.mvvmkotlin.R
import com.zqb.mvvmkotlin.app.TAB_POSITION

/**
 *创建时间:2019/4/17 17:51
 */
class TabFragmentAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment = ImageFragment()
        val bundle = Bundle()
        bundle.putInt(TAB_POSITION, position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return Utils.getApp().resources.getStringArray(R.array.tab).size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Utils.getApp().resources.getStringArray(R.array.tab)[position]
    }
}