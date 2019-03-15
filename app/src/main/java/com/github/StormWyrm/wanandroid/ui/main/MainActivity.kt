package com.github.StormWyrm.wanandroid.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.adapter.ViewPagerAdapter
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.bean.LoginBean
import com.github.StormWyrm.wanandroid.ui.about.AboutActivity
import com.github.StormWyrm.wanandroid.ui.chapter.ChapterFragment
import com.github.StormWyrm.wanandroid.ui.collection.CollectionActivity
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.home.HomeFragment
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.ui.navi.NaviFragment
import com.github.StormWyrm.wanandroid.ui.project.ProjectFragment
import com.github.StormWyrm.wanandroid.ui.search.SearchActivity
import com.github.StormWyrm.wanandroid.ui.tree.TreeFragment
import com.github.StormWyrm.wanandroid.utils.UserUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity() {
    override var isUseEventBus: Boolean = true

    private val fragments: ArrayList<BaseFragment> by lazy {
        arrayListOf<BaseFragment>(
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
    private lateinit var headerview: View
    private lateinit var tvUsername: TextView

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        headerview = navigationView.getHeaderView(0)
        tvUsername = headerview.findViewById(R.id.tvUsername)
        vpMain.adapter = mAdapter
        vpMain.offscreenPageLimit = fragments.size

        tvUsername.text = UserUtils.getUsername()
    }

    override fun initLisenter() {
        super.initLisenter()
        ivHomeMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        ivHomeSearch.setOnClickListener {
            SearchActivity.start(mActivity)
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

        headerview.findViewById<LinearLayout>(R.id.ll_header)
            .setOnClickListener {
                val cookie = UserUtils.getCookie()
                if (cookie.isNullOrEmpty()) {
                    LoginActivity.start(mActivity)
                } else {
//                    ToastUtil.showToast(mActivity,R.string.main_logined)
                }
            }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_left_collect -> collect()
                R.id.main_left_source -> source()
                R.id.main_left_about -> about()
                R.id.main_left_logout -> logout()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAccountEvent(event: LoginBean) {
        val headerView = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.ll_header)
        tvUsername.text = UserUtils.getUsername()
    }

    fun onPositionChange(position: Int) {
        when (position) {
            0 -> tvHomeTitle.setText(R.string.main_home_title)
            1 -> tvHomeTitle.setText(R.string.main_project_title)
            2 -> tvHomeTitle.setText(R.string.main_tree_title)
            3 -> tvHomeTitle.setText(R.string.main_navi_title)
            4 -> tvHomeTitle.setText(R.string.main_chapter_title)
        }
        vpMain.setCurrentItem(position, false)
    }

    private fun logout() {
        AlertDialog.Builder(mActivity)
            .setTitle(R.string.dialog_hit)
            .setMessage(R.string.dialog_logout_hint)
            .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, i: Int ->
                UserUtils.removeCookie()
                UserUtils.removeUsername()
                tvUsername.text = UserUtils.getUsername()
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.show()
    }

    private fun about() {
        AboutActivity.start(mActivity)
    }

    private fun source() {
        ArticleDetailActivity.start(mActivity, getString(R.string.app_name), "https://github.com/StormWyrm/WanAndroid")
    }

    private fun collect() {
        val cookie = UserUtils.getCookie()
        if (cookie.isNullOrEmpty()) {
            AlertDialog.Builder(mActivity)
                .setTitle(R.string.dialog_hit)
                .setMessage(R.string.dialog_login_hint)
                .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, i: Int ->
                    LoginActivity.start(mActivity)
                    dialogInterface.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        } else {
            CollectionActivity.start(mActivity)
        }
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