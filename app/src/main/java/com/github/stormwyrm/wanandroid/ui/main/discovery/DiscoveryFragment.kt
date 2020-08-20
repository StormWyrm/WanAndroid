package com.github.stormwyrm.wanandroid.ui.main.discovery

import android.os.Bundle
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class DiscoveryFragment : BaseFragment() {
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
        fun newInstance(param: String? = null) = DiscoveryFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}