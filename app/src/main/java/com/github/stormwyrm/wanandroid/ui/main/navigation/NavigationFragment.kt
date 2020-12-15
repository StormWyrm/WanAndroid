package com.github.stormwyrm.wanandroid.ui.main.navigation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.ui.main.MainActivity
import com.github.stormwyrm.wanandroid.ui.main.navigation.adapter.NavigationAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.fragment_navigation.viewFlipper

class NavigationFragment : BaseVmFragment<NavigationViewModel>() {

    private lateinit var navigationAdapter: NavigationAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_navigation

    override fun viewModelClass(): Class<NavigationViewModel> = NavigationViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.run {
            loadStatus.observe(viewLifecycleOwner) {
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
            }

            navigationList.observe(viewLifecycleOwner) {
                navigationAdapter.setNewInstance(it)
            }
        }
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initLisenter() {
        super.initLisenter()
        rvNavigation.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getNavigationList()
    }

    private fun setupRecyclerView() {
        navigationAdapter = NavigationAdapter().apply {
            setOnItemClickListener { adapter, view, position -> }
        }
        rvNavigation.adapter = navigationAdapter
    }

    companion object {
        fun newInstance(param: String? = null) = NavigationFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }

}