package com.github.stormwyrm.wanandroid.ui.main.navigation.adapter

import android.view.View
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.common.ext.htmlToSpanned
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_navigation_tag.view.*

class NavigationTagAdapter(
    datas: List<Article>
) : TagAdapter<Article>(datas) {

    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {
        return View.inflate(
            parent?.context,
            R.layout.item_navigation_tag,
            null
        ).apply {
            tvNavigationTag.text = t?.title?.htmlToSpanned()
        }
    }

}