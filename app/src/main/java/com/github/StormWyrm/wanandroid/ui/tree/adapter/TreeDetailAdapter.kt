package com.github.StormWyrm.wanandroid.ui.tree.adapter

import android.text.Html
import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem

class TreeDetailAdapter :
    BaseQuickAdapter<TreeDetailDataItem, BaseViewHolder>(R.layout.item_tree_detail, null) {
    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: TreeDetailDataItem?) {
        helper?.run {
            item?.let {
                setText(R.id.tvTitle, Html.fromHtml(it.title).toString())
                setText(R.id.tvAuthor, App.getApp().getString(R.string.home_time, it.author))
                setText(
                    R.id.tvPushlishTime,
                    App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", it.publishTime))
                )
                getView<ImageView>(R.id.ivStar).run {
                    if (it.collect) {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                    } else {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                    }
                }
            }

            addOnClickListener(R.id.tvAuthor)
            addOnClickListener(R.id.ivStar)
        }
    }

}