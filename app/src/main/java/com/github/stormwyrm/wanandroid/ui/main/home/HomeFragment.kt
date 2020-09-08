package com.github.stormwyrm.wanandroid.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseFragment
import com.github.stormwyrm.wanandroid.common.adapter.SimpleFragmentPagerAdapter
import com.github.stormwyrm.wanandroid.ui.main.MainActivity
import com.github.stormwyrm.wanandroid.ui.search.SearchActivity
import com.github.stormwyrm.wanandroid.ui.main.home.palaza.PalazaFragment
import com.github.stormwyrm.wanandroid.ui.main.home.popularblog.PopularBlogFragment
import com.github.stormwyrm.wanandroid.ui.main.home.popularproject.PopularProjectFragment
import com.github.stormwyrm.wanandroid.ui.main.home.project.ProjectFragment
import com.github.stormwyrm.wanandroid.ui.main.home.wechat.WechatFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    private var currentOffset = 0
    private val titles = arrayListOf(
        StringUtils.getString(R.string.popular_blog),
        StringUtils.getString(R.string.popular_project),
        StringUtils.getString(R.string.plaza),
        StringUtils.getString(R.string.project),
        StringUtils.getString(R.string.wechat)
    )

    private val fragments: List<Fragment> by lazy {
        arrayListOf(
            PopularBlogFragment.newInstance(titles[0]),
            PopularProjectFragment.newInstance(titles[1]),
            PalazaFragment.newInstance(titles[2]),
            ProjectFragment.newInstance(titles[3]),
            WechatFragment.newInstance(titles[4])
        )
    }

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun initView() {
        super.initView()
        viewPager.run {
            adapter = SimpleFragmentPagerAdapter(
                fm = childFragmentManager,
                fragments = fragments,
                titles = titles
            )
            offscreenPageLimit = titles.size
        }
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initLisenter() {
        super.initLisenter()
        chipSearch.setOnClickListener {
            ActivityUtils.startActivity(SearchActivity::class.java)
        }
        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
    }

    companion object {
        fun newInstance(param: String? = null) = HomeFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}