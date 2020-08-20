package com.github.stormwyrm.wanandroid.ui.main.system

import android.os.Bundle
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.ui.main.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class SystemFragment : BaseFragment(){
    private var param: String? = null

    override fun getLayoutResId(): Int = R.layout.fragment_blank

    override fun initData() {
        super.initData()
        param = arguments?.getString("param")
    }

    override fun initView() {
        super.initView()
        param?.run {
            tvMsg.text = this
        }
    }

    companion object {
        fun newInstance(param: String? = null) = SystemFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}