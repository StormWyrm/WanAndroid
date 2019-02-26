package com.github.StormWyrm.wanandroid.ui.tree.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import com.github.StormWyrm.wanandroid.bean.tree.TreeDataItem
import com.qingfeng.flowlayout_ibrary.TagAdapter
import com.qingfeng.flowlayout_ibrary.TagFlowLayout

class TreeAdapter : BaseQuickAdapter<TreeBean, BaseViewHolder>(R.layout.item_tree, null) {
    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {
        helper?.setText(R.id.tvFirstLevelTitle, item?.name)
        helper?.getView<TagFlowLayout>(R.id.flSecondLevelTitle)
            ?.setAdapter(object : TagAdapter<TreeDataItem>(item?.children) {
                override fun getView(p0: TagFlowLayout?, p1: Int, p2: TreeDataItem?): View {
                    return TextView(helper.convertView.context).apply {
                        text = p2?.name
                        setTextColor(context.resources.getColor(R.color.subtextTitleColor))
                    }
                }
            })
    }
}