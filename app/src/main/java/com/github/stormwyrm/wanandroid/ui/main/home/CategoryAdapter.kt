package com.github.stormwyrm.wanandroid.ui.main.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.common.ext.htmlToSpanned
import kotlinx.android.synthetic.main.item_category_sub.view.*

class CategoryAdapter(layoutResId: Int = R.layout.item_category_sub) :
    BaseQuickAdapter<Category, BaseViewHolder>(layoutResId) {

    private var checkedPosition = 0
    var onCheckedListener: ((Int) -> Unit)? = null


    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.itemView.run {
            ctvCategory.text = item.name.htmlToSpanned()
            ctvCategory.isChecked = checkedPosition == holder.adapterPosition

            setOnClickListener {
                val position = holder.adapterPosition
                check(position)
                onCheckedListener?.invoke(position)
            }
        }
    }

    private fun check(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }
}
