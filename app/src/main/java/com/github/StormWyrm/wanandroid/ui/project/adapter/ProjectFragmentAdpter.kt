package com.github.StormWyrm.wanandroid.ui.project.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean
import com.github.StormWyrm.wanandroid.ui.project.category.CategoryFragment

class ProjectFragmentAdpter(val categoryBeans: List<ProjectCategoryBean>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    private val titles = arrayListOf<String>()

    init {
        for (categoryBean in categoryBeans) {
            titles.add(categoryBean.name)
        }
    }

    override fun getItem(position: Int): Fragment {
        return CategoryFragment.newInstance(categoryBeans[position].id)
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