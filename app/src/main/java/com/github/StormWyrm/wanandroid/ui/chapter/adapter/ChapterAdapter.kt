package com.github.StormWyrm.wanandroid.ui.chapter.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean

class ChapterAdapter : BaseQuickAdapter<ChapterBean, BaseViewHolder>(R.layout.item_chaper, null) {
    private val colors = intArrayOf(
        Color.parseColor("#9DD3FA"),
        Color.parseColor("#F7F7D0"),
        Color.parseColor("#FFC09F"),
        Color.parseColor("#A0D8DE"),
        Color.parseColor("#E2DBBE"),
        Color.parseColor("#EAE1F0")
    )

    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder?, item: ChapterBean?) {
        helper?.run {
            setText(R.id.tv_chapter_name, item?.name)
            setBackgroundColor(R.id.tv_chapter_name, colors[layoutPosition % 6])
        }
    }
}