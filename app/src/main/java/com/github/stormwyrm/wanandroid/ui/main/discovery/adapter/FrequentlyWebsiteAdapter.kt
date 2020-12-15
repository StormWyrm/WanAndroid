package com.github.stormwyrm.wanandroid.ui.main.discovery.adapter

import android.view.View
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.FrequentlyWebsite
import com.github.stormwyrm.wanandroid.common.ext.htmlToSpanned
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_frequently_website.view.*

class FrequentlyWebsiteAdapter(
    datas: List<FrequentlyWebsite>
) : TagAdapter<FrequentlyWebsite>(datas) {

    override fun getView(parent: FlowLayout?, position: Int, t: FrequentlyWebsite?): View {
        return View.inflate(
            parent?.context,
            R.layout.item_frequently_website,
            null
        ).apply {
            tvFrequentlyWebsite.text = t?.name?.htmlToSpanned()
        }
    }

}