package com.github.StormWyrm.wanandroid.ui.project.adapter

import android.text.format.DateFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.project.ProjectDataItem

class FragmentCategoryAdapter : BaseQuickAdapter<ProjectDataItem, BaseViewHolder>(R.layout.item_chapter_detail, null) {
    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: ProjectDataItem?) {
        helper?.run{
            setText(R.id.tvTitle, item?.title)
            setText(R.id.tvPushlishTime, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
        }

    }
}