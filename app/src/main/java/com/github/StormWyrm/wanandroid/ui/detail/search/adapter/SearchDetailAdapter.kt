package com.github.StormWyrm.wanandroid.ui.detail.search.adapter

import android.text.Html
import android.text.format.DateFormat
import android.widget.ImageView
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
            item?.let {
                setText(R.id.tvAuthor, App.getApp().getString(R.string.home_author, it.author))
                setText(
                    R.id.tvPushlishTime,
                    App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", it.publishTime))
                )
                setText(R.id.tvTitle, Html.fromHtml(it.title).toString())
                getView<ImageView>(R.id.ivStar).run {
                    if (it.collect) {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like_fill))
                    } else {
                        setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_like))
                    }
                }
            }

            addOnClickListener(R.id.ivStar)
        }
    }

}