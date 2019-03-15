package com.github.StormWyrm.wanandroid.ui.project.category

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import com.github.StormWyrm.wanandroid.bean.project.ProjectDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.ui.project.ProjectCategoryContract
import com.github.StormWyrm.wanandroid.ui.project.ProjectCategoryPresenter
import com.github.StormWyrm.wanandroid.ui.project.adapter.ProjectCategoryAdapter
import com.github.StormWyrm.wanandroid.utils.ToastUtil

class ProjectCategoryFragment : BaseMvpListFragment<ProjectCategoryContract.View, ProjectCategoryContract.Presenter>(),
    ProjectCategoryContract.View {

    override var mPresenter: ProjectCategoryContract.Presenter = ProjectCategoryPresenter()

    private var pageNum = 0
    private val categoryId: Int by lazy {
        arguments?.getInt("categoryId") ?: -1
    }

    private lateinit var mAdapter: ProjectCategoryAdapter

    override fun initView() {
        super.initView()
        mAdapter = ProjectCategoryAdapter().apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tvAuthor -> {
                        getItem(position)?.run {
                            SearchDetailActivity.start(mActivity, author, SearchDetailActivity.AUTHOR)
                        }
                    }
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

    private fun onStarClick(dataItem: ProjectDataItem, position: Int) {
        if (dataItem.collect) {
            mPresenter.requestUncollect(dataItem.id, position)
        } else {
            mPresenter.requestCollect(dataItem.id, position)
        }
    }

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
}