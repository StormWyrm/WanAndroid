package com.github.stormwyrm.wanandroid.ui.main.system.category

import android.view.View
import android.widget.CheckedTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Category
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_system_category.view.*


class SystemCategoryAdapter(
    layoutResId: Int = R.layout.item_system_category,
    data: MutableList<Category>? = null
) : BaseQuickAdapter<Category, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.itemView.run {
            tvTitle.text = item.name
            tagFlowLayout.adapter = object : TagAdapter<Category>(item.children) {
                override fun getView(parent: FlowLayout?, position: Int, t: Category): View {
                    val tv = View.inflate(
                        context,
                        R.layout.item_category_sub,
                        null
                    ) as CheckedTextView
                    tv.text = t.name
                    return tv
                }
            }
        }
    }

}