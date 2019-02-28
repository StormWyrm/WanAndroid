package com.github.StormWyrm.wanandroid.ui.navi.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import com.github.StormWyrm.wanandroid.bean.navi.NaviDataItem
import com.github.StormWyrm.wanandroid.ui.navi.detail.NaviDetailFragment

class NaviFragmentAdapter(val datas: List<NaviBean>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    private val titles = arrayListOf<String>()

    init {
        for (data in datas) {
            titles.add(data.name)
        }
    }

    override fun getItem(position: Int): Fragment {
        return NaviDetailFragment.newInstance(datas[position].articles as ArrayList<NaviDataItem>)
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}