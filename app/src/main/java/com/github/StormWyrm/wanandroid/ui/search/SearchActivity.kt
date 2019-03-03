package com.github.StormWyrm.wanandroid.ui.search

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.github.StormWyrm.flowlayout_library.TagAdapter
import com.github.StormWyrm.flowlayout_library.TagFlowLayout
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpTitleLoadActivity
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseMvpTitleLoadActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {
    override var mPresenter: SearchContract.Presenter = SearchPresenter()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getChildLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initData() {
        super.initData()
        onRetry()
    }

    override fun initView() {
        super.initView()
        initToolbar(getString(R.string.search_title))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_serach, menu)
        val menuItem = menu?.findItem(R.id.searchView)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.apply {
            queryHint = getString(R.string.search_hint)//提示文字
            isIconified = false //搜索框是否默认展开 false:默认展开
            setIconifiedByDefault(true)//搜索的时候，搜索的图标是否在搜索框中

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        SearchDetailActivity.start(mContext, it)
                    } ?: ToastUtil.showToast(mContext, R.string.search_key_empty)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        return true
    }

    override fun onRetry() {
        showLoading()
        mPresenter.requestHotKey()
    }

    override fun onQueryHotSuccess(datas: List<HotKeyBean>) {
        showSuccess()
        if (datas.isNullOrEmpty()) {
            rlHot.visibility = View.GONE
        } else {
            rlHot.visibility = View.VISIBLE
            val adapter = object : TagAdapter<HotKeyBean>(datas) {
                override fun getView(tagFlowLayout: TagFlowLayout?, position: Int, item: HotKeyBean?): View {
                    return (View.inflate(mActivity, R.layout.item_navi_detail, null) as TextView).apply {
                        text = item?.name
                    }
                }
            }
            adapter.setOnTagClickListener { _, _, position ->
                datas[position].let {
                    SearchDetailActivity.start(mContext, it.name)
                }
                true
            }
            tflHot.setAdapter(adapter)
        }
    }
}

