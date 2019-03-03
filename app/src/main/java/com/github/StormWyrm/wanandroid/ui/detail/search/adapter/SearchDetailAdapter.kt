package com.github.StormWyrm.wanandroid.ui.detail.search.adapter

import android.text.format.DateFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem

class SearchDetailAdapter(articles: List<QueryDataItem>?) :
    BaseQuickAdapter<QueryDataItem, BaseViewHolder>(R.layout.item_tree_detail, articles) {

    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: QueryDataItem?) {
        helper?.run {
            setText(R.id.tvAuthor, App.getApp().getString(R.string.home_author, item?.author))
            setText(
                R.id.tvPushlishTime,
                App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
            )
            setText(R.id.tvTitle, item?.title)
        }
    }

}