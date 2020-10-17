package com.github.stormwyrm.wanandroid.ui.main.system

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.common.adapter.SimpleFragmentPagerAdapter
import com.github.stormwyrm.wanandroid.ui.main.home.HomeFragment
import com.github.stormwyrm.wanandroid.ui.main.system.pager.SystemPagerFragment
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.fragment_system.view.*
import kotlinx.android.synthetic.main.fragment_system.viewFlipper
import kotlinx.android.synthetic.main.view_load_error.view.*

class SystemFragment : BaseVmFragment<SystemViewModel>(){

    override fun getLayoutResId(): Int = R.layout.fragment_system

    override fun viewModelClass(): Class<SystemViewModel> = SystemViewModel::class.java

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
                ivFliter.isVisible = true
                tabLayout.isVisible = true
                setup(it)
            })
        }
    }

    override fun initLisenter() {
        super.initLisenter()
        viewFlipper.reload.retry.setOnClickListener {
            mViewModel.loadSystemCategory()
        }

        ivFliter.setOnClickListener {

        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.loadSystemCategory()
    }

    private fun setup(it: MutableList<Category>) {
        val titles = it.map {
            it.name
        }
        val fragments = it.map {
            SystemPagerFragment.newInstance(it.name,it.children)
        }

        val fragmentPagerAdapter = SimpleFragmentPagerAdapter(
            fm = childFragmentManager,
            fragments = fragments,
            titles = titles
        )

        viewPager.adapter = fragmentPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }

    companion object {
        fun newInstance(param: String? = null) = SystemFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}