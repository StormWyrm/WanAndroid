package com.github.StormWyrm.wanandroid.ui.tree.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import com.github.StormWyrm.wanandroid.bean.tree.TreeDataItem
import com.github.StormWyrm.wanandroid.ui.tree.detail.TreeDetailFragment

class TreeDetailFragmentAdpter(private val treeBean: TreeBean, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    private val titles = arrayListOf<String>()
    private val dataItems = arrayListOf<TreeDataItem>()

    init {
        for (treeDataItem in treeBean.children) {
            dataItems.add(treeDataItem)
            titles.add(treeDataItem.name)
        }
    }

    override fun getItem(position: Int): Fragment {
        return TreeDetailFragment.newInstance(dataItems[position].id)
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