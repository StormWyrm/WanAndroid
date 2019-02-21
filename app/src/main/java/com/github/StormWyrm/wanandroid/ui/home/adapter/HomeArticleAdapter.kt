package com.github.StormWyrm.wanandroid.ui.home.adapter

import android.text.format.DateFormat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.article.ArticleDataItem

class HomeArticleAdapter(articles: List<ArticleDataItem>?) :
    BaseQuickAdapter<ArticleDataItem, BaseViewHolder>(R.layout.item_home, articles) {
    init {
        openLoadAnimation()//开启加载动画
    }

    override fun convert(helper: BaseViewHolder?, item: ArticleDataItem?) {
        helper?.setText(R.id.tvTitle, item?.title)
            ?.setText(R.id.tvAuthor, item?.author)
            ?.setText(R.id.tvOrigin, item?.superChapterName)
            ?.setText(R.id.tvPushlishTime, DateFormat.format("yyyy-MM-dd",item?.publishTime ?: 0))
    }

}