package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseFragment
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpLoadFragment
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import com.github.StormWyrm.wanandroid.bean.navi.NaviDataItem
import com.github.StormWyrm.wanandroid.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_navi.*

class NaviFragment : BaseMvpLoadFragment<NaviContract.View, NaviContract.Presenter>(), NaviContract.View {
    override var mPresenter: NaviContract.Presenter = NaviPresenter()

    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    companion object {
        fun newInstance() = NaviFragment()
    }

    override fun getChildLayoutId(): Int {
        return R.layout.fragment_navi
    }

    override fun initListener() {
        super.initListener()
        tabLayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager?.beginTransaction()
                    ?.hide(fragments[oldTabIndex])
                    ?.show(fragments[newTabIndex])
                    ?.commit()
            }
        })
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestNaviList()
    }

    override fun onRequestNaviListSuccess(datas: List<NaviBean>) {
        mStateView.showSuccess()
        val tabNames = arrayListOf<String>()
        for (navBean in datas) {
            tabNames.add(navBean.name)
            fragments.add(NaviDetailFragment.newInstance(navBean.articles as ArrayList<NaviDataItem>))
        }
        tabLayout.addTabs(tabNames)

        initDetailFragment()
    }

    private fun initDetailFragment() {
        val transition = fragmentManager!!.beginTransaction()
        for (f in fragments) {
            transition.add(R.id.fl_container, f).hide(f)
        }
        transition.show(fragments[0])
        transition.commit()
    }
}
