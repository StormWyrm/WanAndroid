package com.github.stormwyrm.wanandroid.ui.main.home.popularblog

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.ui.main.home.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_popular_blog.*
import kotlinx.android.synthetic.main.view_load_error.*

class PopularBlogFragment : BaseVmFragment<PopularBlogViewModel>() {
    private lateinit var mAdapter: ArticleAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_popular_blog

    override fun viewModelClass(): Class<PopularBlogViewModel> = PopularBlogViewModel::class.java

    override fun initView() {
        super.initView()
        refreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList() }
        }

        mAdapter = ArticleAdapter().apply {

            setOnItemChildClickListener { adapter, view, position ->

            }

            setOnItemClickListener { adapter, view, position ->

            }

            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList()
            }
        }

        recyclerView.adapter = mAdapter
    }

    override fun initLisenter() {
        super.initLisenter()
        retry.setOnClickListener {
            mViewModel.refreshArticleList()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewInstance(it)
            })

            loadStatus.observe(viewLifecycleOwner, Observer {
                reload.isVisible = it
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                refreshLayout.isRefreshing = it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.Complete -> mAdapter.loadMoreModule.loadMoreComplete()
                    LoadMoreStatus.Fail -> mAdapter.loadMoreModule.loadMoreFail()
                    LoadMoreStatus.End -> mAdapter.loadMoreModule.loadMoreEnd()
                    else -> return@Observer
                }
            })


        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshArticleList()
    }

    companion object{
        fun newInstance(param: String? = null) = PopularBlogFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }

}