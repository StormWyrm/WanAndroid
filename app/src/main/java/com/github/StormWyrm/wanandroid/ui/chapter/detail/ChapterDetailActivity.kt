package com.github.StormWyrm.wanandroid.ui.chapter.detail

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpListActivity
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterDetailDataItem
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean
import com.github.StormWyrm.wanandroid.bean.project.ProjectDataItem
import com.github.StormWyrm.wanandroid.ui.chapter.adapter.ChapterDetailAdapter
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil

class ChapterDetailActivity : BaseMvpListActivity<ChapterDetailContract.View, ChapterDetailContract.Presenter>(),
    ChapterDetailContract.View {

    override var mPresenter: ChapterDetailContract.Presenter = ChapterDetailPresenter()

    private lateinit var mAdapter: ChapterDetailAdapter
    private var pageNum: Int = 0
    private val chapterId: Int by lazy {
        intent.getIntExtra("chapterId", -1)
    }
    private val chapterName: String by lazy {
        intent.getStringExtra("chapterName")
    }

    companion object {
        fun start(context: Context, chapterId: Int, chapterName: String) {
            val intent = Intent().apply {
                setClass(context, ChapterDetailActivity::class.java)
                putExtra("chapterId", chapterId)
                putExtra("chapterName", chapterName)
            }
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chapter_detail
    }


    override fun initView() {
        super.initView()
        initToolbar(chapterName)

        mAdapter = ChapterDetailAdapter().apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }

            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.ivStar -> {
                        getItem(position)?.run {
                            onStarClick(this, position)
                        }
                    }
                }
            }
        }
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        onRetry()
    }

    override fun initLisenter() {
        super.initLisenter()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestChapterDetailArticle(chapterId, pageNum)
        }
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestChapterDetailArticle(chapterId, pageNum)
    }

    override fun onRequestChapterDetailArticleSuccess(datas: List<ChapterDetailDataItem>) {
        if (pageNum++ == 0) {
            mStateView.showSuccess()
            mAdapter.setNewData(datas)
        } else {
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(datas)
        }
    }

    override fun onCollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = true
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity, R.string.collect_success)
    }

    override fun onUncollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = false
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity, R.string.uncollect_success)
    }

    override fun notLogin() {
        AlertDialog.Builder(mActivity)
            .setTitle(R.string.dialog_hit)
            .setMessage(R.string.dialog_login_hint)
            .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, i: Int ->
                LoginActivity.start(mActivity)
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.show()
    }

    private fun onStarClick(dataItem: ChapterDetailDataItem, position: Int) {
        if (dataItem.collect) {
            mPresenter.requestUncollect(dataItem.id, position)
        } else {
            mPresenter.requestCollect(dataItem.id, position)
        }
    }

}