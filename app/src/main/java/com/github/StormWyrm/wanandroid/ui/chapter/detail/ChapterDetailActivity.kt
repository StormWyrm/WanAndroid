package com.github.StormWyrm.wanandroid.ui.chapter.detail

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpListActivity
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterDetailDataItem
import com.github.StormWyrm.wanandroid.ui.chapter.adapter.ChapterDetailAdapter
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity

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

}