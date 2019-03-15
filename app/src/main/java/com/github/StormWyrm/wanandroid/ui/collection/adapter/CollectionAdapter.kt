package com.github.StormWyrm.wanandroid.ui.collection.adapter

import android.text.Html
import android.text.format.DateFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.article.ArticleDataItem

class CollectionAdapter : BaseQuickAdapter<ArticleDataItem, BaseViewHolder>(R.layout.item_home, null) {
    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: ArticleDataItem?) {
        helper?.run {
            setText(R.id.tvAuthor, App.getApp().getString(R.string.home_author, item?.author))
            setText(
                R.id.tvPushlishTime,
                App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
            )
            setText(R.id.tvTitle, Html.fromHtml(item?.title).toString())
        }
    }
}