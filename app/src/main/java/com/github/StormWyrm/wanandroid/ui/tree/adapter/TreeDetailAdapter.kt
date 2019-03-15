package com.github.StormWyrm.wanandroid.ui.tree.adapter

import android.text.Html
import android.text.format.DateFormat
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
            addOnClickListener(R.id.tvAuthor)

            setText(R.id.tvTitle, Html.fromHtml(item?.title).toString())
            setText(R.id.tvAuthor, App.getApp().getString(R.string.home_time, item?.author))
            setText(
                R.id.tvPushlishTime,
                App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
            )
            addOnClickListener(R.id.ivStar)
        }
    }

}