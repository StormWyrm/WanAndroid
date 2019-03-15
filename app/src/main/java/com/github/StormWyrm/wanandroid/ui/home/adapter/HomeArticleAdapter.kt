package com.github.StormWyrm.wanandroid.ui.home.adapter

import android.text.Html
import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.article.ArticleDataItem

class HomeArticleAdapter(articles: List<ArticleDataItem>?) :
    BaseQuickAdapter<ArticleDataItem, BaseViewHolder>(R.layout.item_home, articles) {

    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: ArticleDataItem?) {
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

                it.tags?.run {
                    if (isEmpty()) {
                        getView<TextView>(R.id.tvOrigin).visibility = View.GONE
                    } else {
                        getView<TextView>(R.id.tvOrigin).visibility = View.VISIBLE
                        setText(R.id.tvOrigin, this[0].name)
                    }
                }
            }
            addOnClickListener(R.id.tvOrigin)
            addOnClickListener(R.id.tvAuthor)
            addOnClickListener(R.id.ivStar)
        }
    }
}
