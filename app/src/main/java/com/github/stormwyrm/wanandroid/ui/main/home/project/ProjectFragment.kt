package com.github.stormwyrm.wanandroid.ui.main.home.project

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.ui.main.home.ArticleAdapter
import com.github.stormwyrm.wanandroid.ui.main.home.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.fragment_project.reload
import kotlinx.android.synthetic.main.view_load_error.view.*

class ProjectFragment : BaseVmFragment<ProjectViewModel>() {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var projectAdapter: ArticleAdapter

    override fun viewModelClass(): Class<ProjectViewModel> {
        return ProjectViewModel::class.java
    }

    override fun getLayoutResId(): Int = R.layout.fragment_project

    override fun initView() {
        super.initView()
        refreshLayout.run {
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setColorSchemeResources(R.color.textColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshProjectList()
            }
        }

        categoryAdapter = CategoryAdapter().apply {
            onCheckedListener = { position: Int ->
                mViewModel.refreshProjectList(position)
            }
        }
        rvCategory.adapter = categoryAdapter

        projectAdapter = ArticleAdapter().apply {

            setOnItemClickListener { adapter, view, position ->

            }

            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreProjectList()
            }
        }
        rvProject.adapter = projectAdapter
    }

    override fun initLisenter() {
        super.initLisenter()
        reload.retry.setOnClickListener {
            mViewModel.getProjectCategories()
        }
        reloadList.retry.setOnClickListener {
            mViewModel.refreshProjectList()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            loadStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadStatus.SUCCESS -> {
                        viewFlipper.displayedChild = 0
                    }
                    LoadStatus.LOADING -> {
                        viewFlipper.displayedChild = 1
                    }
                    LoadStatus.EMPTY -> {
                        viewFlipper.displayedChild = 2
                    }
                    LoadStatus.ERROR -> {
                        viewFlipper.displayedChild = 3
                    }
                }
            })

            categories.observe(viewLifecycleOwner, Observer {
                categoryAdapter.setNewInstance(it)
            })

            loadListStatus.observe(viewLifecycleOwner, Observer {
                reloadList.isVisible = it
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                refreshLayout.isRefreshing = it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.Complete ->{
                        projectAdapter.loadMoreModule.loadMoreComplete()
                    }
                    LoadMoreStatus.End ->{
                        projectAdapter.loadMoreModule.loadMoreEnd()
                    }
                    LoadMoreStatus.Fail ->{
                        projectAdapter.loadMoreModule.loadMoreFail()
                    }
                    else -> return@Observer
                }
            })

            articles.observe(viewLifecycleOwner, Observer {
                projectAdapter.setNewInstance(it)
            })
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getProjectCategories()
    }

    companion object {
        fun newInstance(param: String? = null) = ProjectFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}