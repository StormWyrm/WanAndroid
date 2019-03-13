package com.github.StormWyrm.wanandroid.ui.search

import android.app.AlertDialog
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.github.StormWyrm.flowlayout_library.TagAdapter
import com.github.StormWyrm.flowlayout_library.TagFlowLayout
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpTitleLoadActivity
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.bean.SearchHistory
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search.*
import org.litepal.LitePal

class SearchActivity : BaseMvpTitleLoadActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {
    override var mPresenter: SearchContract.Presenter = SearchPresenter()

    companion object {
        fun start(context: BaseActivity) {
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

    override fun initLisenter() {
        super.initLisenter()
        tvClearRecord.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.dialog_hit)
                .setMessage(R.string.dialog_clear_history)
                .setNegativeButton(R.string.dialog_ok) { dialogInterface, _ ->
                    rlHistory.visibility = View.GONE
                    dialogInterface.dismiss()
                    LitePal.deleteAll(SearchHistory::class.java)
                }
                .setPositiveButton(R.string.dialog_cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .show()
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
                        SearchDetailActivity.start(mActivity, this, SearchDetailActivity.KEY)
                        recordSearchHistory(query)
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
        queryHistory()
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
            }.apply {
                setOnTagClickListener { _, _, position ->
                    getItem(position).run {
                        SearchDetailActivity.start(mActivity, name, SearchDetailActivity.KEY)
                        recordSearchHistory(name)
                    }
                    true
                }
            }
            tflHot.setAdapter(adapter)
        }
    }

    private fun recordSearchHistory(searchKey: String) {
        recordSearchHistory(SearchHistory(searchKey))
    }

    private fun recordSearchHistory(searchHistory: SearchHistory) {
        searchHistory.run {
            val condition = "searchKey = '$searchKey'"
            LitePal.where(condition)
                .findAsync(SearchHistory::class.java)
                .listen {
                    when {
                        it.isNullOrEmpty() -> {
                            saveAsync()
                                .listen { isSuccess ->
                                    if (isSuccess) {
                                        queryHistory()
                                    }
                                }
                        }
                        else -> {
                            LitePal.deleteAllAsync(SearchHistory::class.java, condition)
                                .listen { changeCount ->
                                    if (changeCount > 0) {
                                        saveAsync()
                                            .listen { isSuccess ->
                                                if (isSuccess) {
                                                    queryHistory()
                                                }
                                            }
                                    }

                                }
                        }
                    }
                }
        }
    }

    private fun queryHistory() {

        LitePal.order("id desc")
            .limit(10)
            .findAsync(SearchHistory::class.java)
            .listen {
                if (it.isNullOrEmpty()) {
                    rlHistory.visibility = View.GONE
                } else {
                    rlHistory.visibility = View.VISIBLE
                    val adapter = object : TagAdapter<SearchHistory>(it) {
                        override fun getView(tagFlowLayout: TagFlowLayout?, position: Int, item: SearchHistory?): View {
                            return (View.inflate(mActivity, R.layout.item_navi_detail, null) as TextView).apply {
                                text = item?.searchKey
                            }
                        }
                    }.apply {
                        setOnTagClickListener { _, _, position ->
                            getItem(position).run {
                                SearchDetailActivity.start(mActivity, searchKey, SearchDetailActivity.KEY)
                                recordSearchHistory(this.searchKey)
                            }
                            true
                        }
                    }
                    tflHistory.setAdapter(adapter)
                }
            }
    }
}

