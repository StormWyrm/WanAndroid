package com.github.stormwyrm.wanandroid.ui.main.system.pager

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.ui.main.home.ArticleAdapter
import com.github.stormwyrm.wanandroid.ui.main.home.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_system_pager.*

class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>() {
    private lateinit var categories: ArrayList<Category>
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var articleAdapter: ArticleAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_system_pager

    override fun viewModelClass(): Class<SystemPagerViewModel> {
        return SystemPagerViewModel::class.java
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articles.observe(viewLifecycleOwner, Observer {
                articleAdapter.setNewInstance(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                refreshLayout.isRefreshing = it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.Complete -> {
                        articleAdapter.loadMoreModule.loadMoreComplete()
                    }
                    LoadMoreStatus.End -> {
                        articleAdapter.loadMoreModule.loadMoreEnd()
                    }
                    LoadMoreStatus.Fail -> {
                        articleAdapter.loadMoreModule.loadMoreFail()
                    }
                    else -> return@Observer
                }
            })

            loadListStatus.observe(viewLifecycleOwner, Observer {
                reloadList.isVisible = it
            })
        }
    }

    override fun initData() {
        super.initData()
        categories = arguments?.getParcelableArrayList<Category>("category") ?: ArrayList()
        if (categories.isNullOrEmpty()) {
            reload.isVisible = true
        }
    }

    override fun initView() {
        super.initView()
        refreshLayout.run {
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setColorSchemeResources(R.color.textColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshSystemArticle(categories[categoryAdapter.checkedPosition].id)
            }
        }

        categoryAdapter = CategoryAdapter(datas = categories).apply {
            onCheckedListener = {
                mViewModel.refreshSystemArticle(categories[it].id)
            }
        }

        rvCategory.adapter = categoryAdapter

        articleAdapter = ArticleAdapter().apply {
            setOnItemClickListener { adapter, view, position ->

            }
            loadMoreModule.isEnableLoadMore = true
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreSystemArticle()
            }
        }

        rvArticle.adapter = articleAdapter
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        if (!categories.isNullOrEmpty())
            mViewModel.refreshSystemArticle(categories[categoryAdapter.checkedPosition].id)
    }

    companion object {
        fun newInstance(param: String? = null, categories: ArrayList<Category>) =
            SystemPagerFragment().apply {
                param?.let {
                    arguments = Bundle().apply {
                        putString("param", it)
                        putParcelableArrayList("category", categories)
                    }
                }
            }
    }
}