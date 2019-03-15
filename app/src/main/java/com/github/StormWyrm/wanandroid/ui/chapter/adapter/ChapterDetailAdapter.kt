package com.github.StormWyrm.wanandroid.ui.chapter.adapter

import android.text.Html
import android.text.format.DateFormat
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
            setText(R.id.tvTitle, Html.fromHtml(item?.title).toString())
            setText(
                R.id.tvPushlishTime,
                App.getApp().getString(R.string.home_time, DateFormat.format("yyyy-MM-dd", item?.publishTime ?: 0))
            )
            addOnClickListener(R.id.ivStar)
        }
    }
}