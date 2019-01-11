package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.ui.navi.NaviFragment
import kotlinx.android.synthetic.main.fragment_base.*

class ProjectFragment : BaseFragment() {
    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        super.initView()
        tvContent.setText(R.string.main_project_title)
    }
}