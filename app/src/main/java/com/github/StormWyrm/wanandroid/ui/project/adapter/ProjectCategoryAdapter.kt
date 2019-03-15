package com.github.StormWyrm.wanandroid.ui.project.adapter

import android.text.Html
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
            item?.let {
                setText(R.id.tvTitle, Html.fromHtml(it.title).toString())
                setText(R.id.tvContent, Html.fromHtml(it.desc).toString())
                setText(R.id.tvAuthor, App.getApp().getString(R.string.home_author, it.author))
                setText(
                    R.id.tvPushlishTime,
                    App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
                )

                getView<ImageView>(R.id.ivStar).run {
                    if (it.collect) {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                    } else {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                    }
                }

                val ivThumb = getView<ImageView>(R.id.ivThumb)
                Glide.with(mContext).load(item.envelopePic).into(ivThumb)
            }

            addOnClickListener(R.id.tvAuthor)

            addOnClickListener(R.id.ivStar)
        }

    }
}