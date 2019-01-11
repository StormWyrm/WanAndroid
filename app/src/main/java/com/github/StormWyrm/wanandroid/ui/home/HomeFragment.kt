package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_base.*

class HomeFragment : BaseFragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        super.initView()
        tvContent.setText(R.string.main_home_title)
    }
}