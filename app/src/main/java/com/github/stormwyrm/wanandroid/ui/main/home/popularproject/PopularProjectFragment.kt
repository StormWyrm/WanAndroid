package com.github.stormwyrm.wanandroid.ui.main.home.popularproject

import android.os.Bundle
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class PopularProjectFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_blank

    override fun initView() {
        super.initView()
        param?.run {
            tvMsg.text = this
        }
    }

    companion object {
        fun newInstance(param: String? = null) = PopularProjectFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}