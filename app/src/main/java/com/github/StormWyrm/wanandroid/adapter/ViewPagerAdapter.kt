package com.github.StormWyrm.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment

class ViewPagerAdapter(
    private val fragments: List<BaseFragment>,
    private val titles: List<String>? = null,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun getItem(position: Int): Fragment = fragments[position]

    /**
     * Return the number of views available.
     */
    override fun getCount(): Int = fragments.size


    override fun getPageTitle(position: Int): CharSequence? = titles?.get(position) ?: super.getPageTitle(position)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}