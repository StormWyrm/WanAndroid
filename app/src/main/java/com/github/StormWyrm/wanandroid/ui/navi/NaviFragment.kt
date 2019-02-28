package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpLoadFragment
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import com.github.StormWyrm.wanandroid.ui.navi.adapter.NaviFragmentAdapter
import com.github.StormWyrm.wanandroid.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_navi.*

class NaviFragment : BaseMvpLoadFragment<NaviContract.View, NaviContract.Presenter>(), NaviContract.View {
    override var mPresenter: NaviContract.Presenter = NaviPresenter()

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
                vvp.setCurrentItem(newTabIndex, false)
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
        }
        tabLayout.addTabs(tabNames)
        vvp.adapter = NaviFragmentAdapter(datas, childFragmentManager)
    }

}
