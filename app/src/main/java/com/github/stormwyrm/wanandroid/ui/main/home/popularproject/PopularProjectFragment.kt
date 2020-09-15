package com.github.stormwyrm.wanandroid.ui.main.home.popularproject

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.ui.main.home.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_popular_project.*

class PopularProjectFragment : BaseVmFragment<PopularProjectViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    override fun viewModelClass(): Class<PopularProjectViewModel> =
        PopularProjectViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_popular_project

    override fun observe() {
        super.observe()
        mViewModel.run {
            projectList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewInstance(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                refreshLayout.isRefreshing = it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.Complete -> {
                        mAdapter.loadMoreModule.loadMoreComplete()
                    }
                    LoadMoreStatus.Fail -> {
                        mAdapter.loadMoreModule.loadMoreFail()
                    }
                    LoadMoreStatus.End -> {
                        mAdapter.loadMoreModule.loadMoreEnd()
                    }
                    else -> return@Observer
                }
            })
        }
    }

    override fun initView() {
        super.initView()
        refreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshProjectList() }
        }

        mAdapter = ArticleAdapter().apply {
            setOnItemClickListener { adapter, view, position ->

            }
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreProjectList()
            }
        }
        recyclerView.adapter = mAdapter
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshProjectList()
    }


    companion object {
        fun newInstance(param: String? = null) = PopularProjectFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}