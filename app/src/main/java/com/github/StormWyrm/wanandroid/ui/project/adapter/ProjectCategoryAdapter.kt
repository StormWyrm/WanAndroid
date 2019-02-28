package com.github.StormWyrm.wanandroid.ui.project.adapter

import android.text.format.DateFormat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.project.ProjectDataItem

class ProjectCategoryAdapter : BaseQuickAdapter<ProjectDataItem, BaseViewHolder>(R.layout.item_project, null) {
    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: ProjectDataItem?) {
        helper?.apply {
            setText(R.id.tvTitle, item?.title)
            setText(R.id.tvContent, item?.desc)
            setText(R.id.tvAuthor, App.getApp().getString(R.string.home_author, item?.author))
            setText(
                R.id.tvPushlishTime,
                App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
            )

            val ivThumb = getView<ImageView>(R.id.ivThumb)
            Glide.with(helper.convertView.context).load(item?.envelopePic).into(ivThumb)
        }

    }
}