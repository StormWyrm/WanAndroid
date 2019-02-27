package com.github.StormWyrm.wanandroid.ui.chapter

import androidx.recyclerview.widget.GridLayoutManager
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean
import com.github.StormWyrm.wanandroid.ui.chapter.adapter.ChapterAdapter
import com.github.StormWyrm.wanandroid.ui.chapter.detail.ChapterDetailActivity

class ChapterFragment : BaseMvpListFragment<ChapterContract.View, ChapterContract.Presenter>(), ChapterContract.View {
    override var mPresenter: ChapterContract.Presenter = ChapterPresenter()
    override val isEnableLoadmore: Boolean
        get() = false

    private lateinit var mAdapter: ChapterAdapter

    companion object {
        fun newInstance() = ChapterFragment()
    }

    override fun initView() {
        super.initView()
        mAdapter = ChapterAdapter().apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.run {
                    ChapterDetailActivity.start(mActivity, id, name)
                }
            }
        }
        mRecyclerView.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestChapterList()
    }

    override fun onRequestChapterListSuccess(datas: List<ChapterBean>) {
        mStateView.showSuccess()
        mAdapter.setNewData(datas)
    }
}
