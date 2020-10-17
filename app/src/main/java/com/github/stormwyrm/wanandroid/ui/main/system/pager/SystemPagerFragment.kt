package com.github.stormwyrm.wanandroid.ui.main.system.pager

import android.os.Bundle
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.bean.Category
import kotlinx.android.synthetic.main.fragment_blank.*

class SystemPagerFragment : BaseFragment() {
    private lateinit var childCategories: MutableList<Category>

    override fun getLayoutResId(): Int = R.layout.fragment_blank

    override fun initView() {
        super.initView()
        param?.run {
            tvMsg.text = this
        }

    }

    companion object {
        fun newInstance(param: String? = null, categories: ArrayList<Category>) =
            SystemPagerFragment().apply {
                param?.let {
                    arguments = Bundle().apply {
                        putString("param", it)
                        putParcelableArrayList("category", categories)
                    }
                }
            }
    }
}