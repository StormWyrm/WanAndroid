package com.github.stormwyrm.wanandroid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.StringUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseActivity
import com.github.stormwyrm.wanandroid.ui.main.discovery.DiscoveryFragment
import com.github.stormwyrm.wanandroid.ui.main.home.HomeFragment
import com.github.stormwyrm.wanandroid.ui.main.mine.MineFragment
import com.github.stormwyrm.wanandroid.ui.main.navigation.NavigationFragment
import com.github.stormwyrm.wanandroid.ui.main.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var mFragments: Map<Int, Fragment>

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mFragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.discovery to createFragment(DiscoveryFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bottomNavigationView.selectedItemId = initialItemId
            showFragment(initialItemId)
        }
    }

    override fun initLisenter() {
        super.initLisenter()
        bottomNavigationView.run {
            setOnNavigationItemSelectedListener {
                showFragment(it.itemId)
                true
            }
        }
    }

    private fun showFragment(itemId: Int) {
        val curFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in mFragments.values
        }
        val targetFragment = mFragments[itemId]
        supportFragmentManager.beginTransaction().run {
            curFragment?.run {
                if (isVisible)
                    hide(this)
            }

            targetFragment?.run {
                if (isAdded)
                    show(this)
                else
                    add(R.id.fragmentContainerView, this)
            }
            commit()
        }
    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment =
            supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance(StringUtils.getString(R.string.home))
                SystemFragment::class.java -> SystemFragment.newInstance(StringUtils.getString(R.string.system))
                DiscoveryFragment::class.java -> DiscoveryFragment.newInstance(StringUtils.getString(R.string.discovery))
                NavigationFragment::class.java -> NavigationFragment.newInstance(StringUtils.getString(R.string.navigation))
                MineFragment::class.java -> MineFragment.newInstance(StringUtils.getString(R.string.mine))
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }


}
