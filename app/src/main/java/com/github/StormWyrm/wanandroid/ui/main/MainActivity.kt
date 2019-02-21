package com.github.StormWyrm.wanandroid.ui.main

import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.adapter.ViewPagerAdapter
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpActivity
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.ui.chapter.ChapterFragment
import com.github.StormWyrm.wanandroid.ui.home.HomeFragment
import com.github.StormWyrm.wanandroid.ui.navi.NaviFragment
import com.github.StormWyrm.wanandroid.ui.project.ProjectFragment
import com.github.StormWyrm.wanandroid.ui.tree.TreeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>() {

    override var mPresenter: MainContract.Presenter = MainPresenter()

    private val fragments: ArrayList<BaseFragment> by lazy {
        arrayListOf(
            HomeFragment.newInstance(),
            ProjectFragment.newInstance(),
            TreeFragment.newInstance(),
            NaviFragment.newInstance(),
            ChapterFragment.newInstance()
        )
    }
    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(fragments = fragments, fm = supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initLisenter() {
        super.initLisenter()
        ivHomeMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        vpMain.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                onPositionChange(position)
                this@MainActivity.onPageSelected(position)
            }
        })
        bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_bottom_home -> onPositionChange(0)
                R.id.main_bottom_project -> onPositionChange(1)
                R.id.main_bottom_tree -> onPositionChange(2)
                R.id.main_bottom_navi -> onPositionChange(3)
                R.id.main_bottom_chapter -> onPositionChange(4)
            }
            return@setOnNavigationItemSelectedListener true
        }

        nvaigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_left_collect -> collect()
                R.id.main_left_setting -> setting()
                R.id.main_left_about -> about()
                R.id.main_left_logout -> logout()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener false
        }
    }

    override fun initView() {
        super.initView()
        vpMain.adapter = mAdapter
        vpMain.offscreenPageLimit = fragments.size
    }

    private fun logout() {

    }

    private fun about() {

    }

    private fun setting() {

    }

    private fun collect() {

    }

    private fun onPositionChange(position: Int) {
        when (position) {
            0 -> tvHomeTitle.setText(R.string.main_home_title)
            1 -> tvHomeTitle.setText(R.string.main_project_title)
            2 -> tvHomeTitle.setText(R.string.main_tree_title)
            3 -> tvHomeTitle.setText(R.string.main_navi_title)
            4 -> tvHomeTitle.setText(R.string.main_chapter_title)
        }
        vpMain.setCurrentItem(position, false)
    }

    private fun onPageSelected(postion: Int) {
        when (postion) {
            0 -> bnv.selectedItemId = R.id.main_bottom_home
            1 -> bnv.selectedItemId = R.id.main_bottom_project
            2 -> bnv.selectedItemId = R.id.main_bottom_tree
            3 -> bnv.selectedItemId = R.id.main_bottom_navi
            4 -> bnv.selectedItemId = R.id.main_bottom_chapter
        }
    }

}