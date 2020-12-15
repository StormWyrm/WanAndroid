package com.github.stormwyrm.wanandroid.ui.main.discovery.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.HotWord
import com.github.stormwyrm.wanandroid.common.ext.htmlToSpanned
import kotlinx.android.synthetic.main.item_hot_word.view.*

class HotWordAdapter(
    datas: MutableList<HotWord>? = null,
    layoutResId: Int = R.layout.item_hot_word
) : BaseQuickAdapter<HotWord, BaseViewHolder>(layoutResId, datas) {

    override fun convert(holder: BaseViewHolder, item: HotWord) {
        holder.itemView.run {
            tvHotWord.text = item.name.htmlToSpanned()
        }
    }
}