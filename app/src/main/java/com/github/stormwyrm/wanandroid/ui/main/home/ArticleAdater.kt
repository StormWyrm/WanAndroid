package com.github.stormwyrm.wanandroid.ui.main.home

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.common.ext.htmlToSpanned
import kotlinx.android.synthetic.main.item_article.view.*


class ArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutResId), LoadMoreModule {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.run {
            itemView.run {
                tvTop.isVisible = item.top

                tvAuthor.text = when {
                    !item.author.isNullOrEmpty() -> item.author
                    !item.shareUser.isNullOrEmpty() -> item.shareUser
                    else -> StringUtils.getString(R.string.anonymous)
                }

                tvTag.isVisible = if (item.tags.isNotEmpty()) {
                    tvTag.text = item.tags[0].name
                    true
                } else {
                    false
                }

                tvChapter.text = when {
                    !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                    item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        item.chapterName.htmlToSpanned()
                    !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                        item.superChapterName.htmlToSpanned()
                    else -> ""

                }

                tvTitle.text = item.title.htmlToSpanned()
                tvDesc.text = item.desc.htmlToSpanned()
                tvDesc.isGone = item.desc.isNullOrEmpty()

                tvFresh.isVisible = item.fresh
                tvTime.text = item.niceDate
            }

        }

    }
}