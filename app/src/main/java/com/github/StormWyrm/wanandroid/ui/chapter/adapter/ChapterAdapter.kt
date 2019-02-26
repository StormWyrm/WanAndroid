package com.github.StormWyrm.wanandroid.ui.chapter.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean

class ChapterAdapter : BaseQuickAdapter<ChapterBean, BaseViewHolder>(R.layout.item_chaper, null) {

    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder?, item: ChapterBean?) {
        helper?.run {
            setText(R.id.tv_chapter_name, item?.name)
        }
    }
}