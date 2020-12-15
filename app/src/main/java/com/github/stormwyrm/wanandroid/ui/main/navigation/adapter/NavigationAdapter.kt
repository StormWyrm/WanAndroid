package com.github.stormwyrm.wanandroid.ui.main.navigation.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Navigation
import kotlinx.android.synthetic.main.item_navigation.view.*

class NavigationAdapter(
    layoutResId: Int = R.layout.item_navigation,
    datas: MutableList<Navigation>? = null
) : BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId, datas) {

    override fun convert(holder: BaseViewHolder, item: Navigation) {
        holder.itemView.run {
            tvNavigationTitle.text = item.name
            tflNavigation.adapter = NavigationTagAdapter(
                item.articles
            ).apply {

            }
        }
    }
}