package com.github.StormWyrm.wanandroid.ui.search

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.flowlayout_library.TagAdapter
import com.github.StormWyrm.flowlayout_library.TagFlowLayout
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpTitleLoadActivity
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import com.github.StormWyrm.wanandroid.ui.detail.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.search.adapter.SearchQueryAdapter
import com.github.StormWyrm.wanandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SearchActivity : BaseMvpTitleLoadActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {
    override var mPresenter: SearchContract.Presenter = SearchPresenter()

    private var pageNum: Int = 0
    private lateinit var queryKey: String
    private lateinit var mSearchQueryAdapter: SearchQueryAdapter

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

        mSearchQueryAdapter = SearchQueryAdapter(null).apply {
            setOnItemClickListener { adapter, view, position ->
                getItem(position)?.apply {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
        }

        list_rv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mSearchQueryAdapter
        }

        refreshLayout.run {
            isEnableRefresh = false
            isEnableAutoLoadMore = true
            setOnLoadMoreListener {
                mPresenter.requestQueryKey(queryKey, pageNum)
            }
        }
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
                    query?.run {
                        pageNum = 0
                        queryKey = query
                        showSearchResult()
                        mSearchQueryAdapter.setEmptyView(R.layout.layout_loading_view, list_rv)
                        mPresenter.requestQueryKey(query, pageNum)
                    } ?: ToastUtil.showToast(mContext, R.string.search_key_empty)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            setOnCloseListener {
                pageNum = 0
                showHotKey()
                false
            }
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
                datas[position].run {
                    pageNum = 0
                    queryKey = name
                    showSearchResult()
                    mSearchQueryAdapter.setEmptyView(R.layout.layout_loading_view, list_rv)
                    mPresenter.requestQueryKey(name, pageNum)
                }
                true
            }
            tflHot.setAdapter(adapter)
        }
    }

    override fun onQueryKeySuccess(data: List<QueryDataItem>) {
        if (pageNum++ == 0) {
            mSearchQueryAdapter.setNewData(data)
        } else {
            mSearchQueryAdapter.addData(data)
        }
    }

    override fun onQueryKeyEmpty() {
        mSearchQueryAdapter.setEmptyView(R.layout.layout_empty_view, list_rv)
    }

    override fun onQueryKeyError() {
        val errorView = View.inflate(mContext, R.layout.layout_error_view, null)
        errorView.findViewById<Button>(R.id.btn_retry).setOnClickListener {
            mSearchQueryAdapter.setEmptyView(R.layout.layout_loading_view, list_rv)
            mPresenter.requestQueryKey(queryKey, pageNum)
        }
        mSearchQueryAdapter.emptyView = errorView
    }

    override fun onQueryMoreKeyError() {
        refreshLayout.finishLoadMore(false)
    }

    override fun onQueryMoreKeyEmpty() {
        refreshLayout.setNoMoreData(true)
    }

    private fun showSearchResult() {
        refreshLayout.visibility = View.VISIBLE
        svSearch.visibility = View.GONE
    }

    private fun showHotKey() {
        refreshLayout.visibility = View.GONE
        svSearch.visibility = View.VISIBLE
    }

    private fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}

