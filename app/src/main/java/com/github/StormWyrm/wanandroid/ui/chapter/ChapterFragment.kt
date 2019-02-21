package com.github.StormWyrm.wanandroid.ui.chapter

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_base.*

class ChapterFragment : BaseFragment() {

    companion object {
        fun newInstance() = ChapterFragment()
    }

    override fun getLayoutId(): Int  = R.layout.fragment_base

    override fun initView() {
        super.initView()

        tvContent.setText(R.string.main_chapter_title)
    }
}