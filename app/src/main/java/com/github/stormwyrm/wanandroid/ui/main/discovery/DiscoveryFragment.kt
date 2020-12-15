package com.github.stormwyrm.wanandroid.ui.main.discovery

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.observe
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseVmFragment
import com.github.stormwyrm.wanandroid.bean.Banner
import com.github.stormwyrm.wanandroid.bean.FrequentlyWebsite
import com.github.stormwyrm.wanandroid.bean.HotWord
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.ui.main.MainActivity
import com.github.stormwyrm.wanandroid.ui.search.SearchActivity
import com.youth.banner.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_discovery.*


@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class DiscoveryFragment : BaseVmFragment<DiscoveryViewModel>() {

    private lateinit var hotWordAdapter: HotWordAdapter

    override fun getLayoutResId(): Int = R.layout.fragment_discovery

    override fun viewModelClass(): Class<DiscoveryViewModel> = DiscoveryViewModel::class.java

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
            banners.observe(viewLifecycleOwner) {
                setupBanner(it)
            }

            hotWords.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    tvHotWord.isVisible = false
                    rvHotWord.isVisible = false
                } else {
                    tvHotWord.isVisible = true
                    rvHotWord.isVisible = true
                    hotWordAdapter.setNewInstance(it)
                }
            }

            frequentlyWebsites.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {

                } else {
                    setupWebsites(it)
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        bannerView.addBannerLifecycleObserver(viewLifecycleOwner)
        setupHotwords()
    }

    override fun initLisenter() {
        super.initLisenter()
        ivAdd.setOnClickListener {

        }

        ivSearch.setOnClickListener {
            ActivityUtils.startActivity(SearchActivity::class.java)
        }
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getData()
    }

    private fun setupBanner(datas: List<Banner>) {
        bannerView.run {
            setPageTransformer(DepthPageTransformer())

            adapter = BannerImageAdapter(datas).apply {
                setOnBannerListener { data, position ->
                    ToastUtils.showShort((data as Banner).title)
                }
            }
        }
    }

    private fun setupHotwords() {
        hotWordAdapter = HotWordAdapter().apply {
            setOnItemClickListener { adapter, view, position ->
                ToastUtils.showShort((adapter.data[position] as HotWord).name)
            }
        }
        rvHotWord.adapter = hotWordAdapter
    }

    private fun setupWebsites(datas: List<FrequentlyWebsite>) {
        tagFlowLayout.run {
            setOnTagClickListener { view, position, parent ->
                ToastUtils.showShort(datas[position].name)
                return@setOnTagClickListener true
            }
            adapter = FrequentlyWebsiteAdapter(datas)
        }
    }

    companion object {
        fun newInstance(param: String? = null) = DiscoveryFragment().apply {
            param?.let {
                arguments = Bundle().apply {
                    putString("param", it)
                }
            }
        }
    }
}