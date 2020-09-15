package com.github.stormwyrm.wanandroid.ui.main.home.palaza

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.ui.main.home.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_palaza.*

class PalazaFragment : BaseVmFragment<PalazaViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    override fun viewModelClass(): Class<PalazaViewModel> {
        return PalazaViewModel::class.java
    }

    override fun getLayoutResId(): Int = R.layout.fragment_palaza

    override fun initView() {
        super.initView()
        refreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshArticleList()
            }
        }

        mAdapter = ArticleAdapter().apply {
            setOnItemClickListener { adapter, view, position ->

            }
            loadMoreModule.setOnLoadMoreListener {
                mViewModel.loadMoreArticleList()
            }
        }

        recyclerView.adapter = mAdapter
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
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

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.refreshArticleList()
    }

    companion object {
        fun newInstance(param: String? = null) = PalazaFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }

}