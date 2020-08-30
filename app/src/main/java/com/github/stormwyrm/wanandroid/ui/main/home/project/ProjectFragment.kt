package com.github.stormwyrm.wanandroid.ui.main.home.project

import android.os.Bundle
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.ui.main.mine.MineFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class ProjectFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_blank

    override fun initView() {
        super.initView()
        param?.run {
            tvMsg.text = this
        }
    }
    companion object{
        fun newInstance(param: String? = null) = ProjectFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}