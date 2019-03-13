package com.github.StormWyrm.wanandroid.ui.navi.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.StormWyrm.flowlayout_library.TagAdapter
import com.github.StormWyrm.flowlayout_library.TagFlowLayout
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.bean.navi.NaviDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import kotlinx.android.synthetic.main.fragment_navi_detail.*

class NaviDetailFragment : BaseFragment() {

    private lateinit var datas: ArrayList<NaviDataItem>

    companion object {
        fun newInstance(param: ArrayList<NaviDataItem>) =
            NaviDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("naviDataItem", param)
                }
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navi_detail
    }

    override fun initLoad() {
        super.initLoad()
        datas = arguments?.run {
            getParcelableArrayList<NaviDataItem>("naviDataItem")
        } ?: throw RuntimeException()
        val adapter = object : TagAdapter<NaviDataItem>(datas) {
            override fun getView(tagFlowLayout: TagFlowLayout?, position: Int, item: NaviDataItem?): View {
                return (View.inflate(mContext, R.layout.item_navi_detail, null) as TextView)
                    .apply {
                        text = item?.title
                    }
            }
        }.apply {
            setOnTagClickListener { _, _, position ->
                getItem(position).run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
                true
            }
        }
        tfl.setAdapter(adapter)
    }

}