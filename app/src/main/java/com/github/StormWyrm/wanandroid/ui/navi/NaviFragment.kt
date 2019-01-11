package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_base.*

class NaviFragment : BaseFragment(){
    companion object {
        fun newInstance() = NaviFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        super.initView()
        tvContent.setText(R.string.main_navi_title)
    }
}