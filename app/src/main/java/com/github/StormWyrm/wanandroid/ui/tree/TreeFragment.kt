package com.github.StormWyrm.wanandroid.ui.tree

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.ui.navi.NaviFragment
import kotlinx.android.synthetic.main.fragment_base.*

class TreeFragment : BaseFragment() {
    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        super.initView()
        tvContent.setText(R.string.main_tree_title)
    }
}