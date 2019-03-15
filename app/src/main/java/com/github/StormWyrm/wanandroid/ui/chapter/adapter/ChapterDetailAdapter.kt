package com.github.StormWyrm.wanandroid.ui.chapter.adapter

import android.text.Html
import android.text.format.DateFormat
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterDetailDataItem

class ChapterDetailAdapter :
    BaseQuickAdapter<ChapterDetailDataItem, BaseViewHolder>(R.layout.item_chapter_detail, null) {

    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder?, item: ChapterDetailDataItem?) {
        helper?.apply {

            item?.let {
                setText(R.id.tvTitle, Html.fromHtml(it.title).toString())
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
            addOnClickListener(R.id.ivStar)
        }
    }
}