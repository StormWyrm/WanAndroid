package com.github.StormWyrm.wanandroid.ui.project.category

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.project.ProjectCategoryContract
import com.github.StormWyrm.wanandroid.ui.project.ProjectCategoryPresenter
import com.github.StormWyrm.wanandroid.ui.project.adapter.ProjectCategoryAdapter

class ProjectCategoryFragment : BaseMvpListFragment<ProjectCategoryContract.View, ProjectCategoryContract.Presenter>(),
    ProjectCategoryContract.View {

    override var mPresenter: ProjectCategoryContract.Presenter = ProjectCategoryPresenter()

    private var pageNum = 0
    private val categoryId: Int by lazy {
        arguments?.getInt("categoryId") ?: -1
    }

    private lateinit var mAdapter: ProjectCategoryAdapter


    companion object {
        fun newInstance(categoryId: Int): ProjectCategoryFragment {
            val bundle = Bundle().apply {
                putInt("categoryId", categoryId)
            }
            return ProjectCategoryFragment().apply {
                arguments = bundle
            }
        }

    }

    override fun initView() {
        super.initView()
        mAdapter = ProjectCategoryAdapter().apply {
            setOnItemClickListener { adapter, _, position ->
                getItem(position)?.run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
        }
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    override fun initListener() {
        super.initListener()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestProjectAricle(pageNum, categoryId)
        }
    }

    override fun initLoad() {
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestProjectAricle(pageNum, categoryId)
    }

    override fun onrequestProjectAricleSuccess(categoryBeans: ProjectBean) {
        if (pageNum++ == 0) {
            mStateView.showSuccess()
            mAdapter.setNewData(categoryBeans.datas)
        } else {
            mAdapter.addData(categoryBeans.datas)
            mRefreshLayout.finishLoadMore(true)
        }
    }
}