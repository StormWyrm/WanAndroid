package com.github.StormWyrm.wanandroid.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.LoginBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleDataItem
import com.github.StormWyrm.wanandroid.dp2px
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.ui.home.adapter.HomeArticleAdapter
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.ui.main.MainActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseMvpListFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    override var mPresenter: HomeContract.Presenter = HomePresenter()
    private lateinit var mAdapter: HomeArticleAdapter
    private var pageNum: Int = 0
    private lateinit var banner: Banner
    private lateinit var bannerList: List<BannerBean>

    override fun initView() {
        super.initView()
        banner = (View.inflate(mContext, R.layout.layout_home_header, null) as Banner)
            .apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(180f))
                setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)//设置banner样式-数字+标题
                setIndicatorGravity(BannerConfig.RIGHT)//设置指针的位置
                setBannerAnimation(Transformer.DepthPage)//设置切换动画
                isAutoPlay(true)
                setDelayTime(5000)
                setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                        Glide.with(context).load(path).into(imageView)
                    }
                })
                setOnBannerListener { position ->
                    bannerList[position].run {
                        ArticleDetailActivity.start(mActivity, title, url)
                    }
                }
            }
        mAdapter = HomeArticleAdapter(null).apply {
            addHeaderView(banner)
            setOnItemClickListener { _, _, position ->
                getItem(position)?.run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tvOrigin -> {
                        onOriginClick((view as TextView).text.toString())
                    }

                    R.id.ivStar -> {
                        getItem(position)?.run {
                            onStarClick(this,position)
                        }
                    }

                    R.id.tvAuthor -> {
                        getItem(position)?.run {
                            SearchDetailActivity.start(mActivity, author, SearchDetailActivity.AUTHOR)
                        }
                    }
                }
            }
        }
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestArticleList(pageNum)
        }
    }

    override fun onResume() {
        super.onResume()
        banner.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        banner.stopAutoPlay()
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestBanner()
        mPresenter.requestArticleList(pageNum)
    }

    override fun onRequestBannerSuccess(bannerList: List<BannerBean>) {
        if (!bannerList.isNullOrEmpty()) {
            this.bannerList = bannerList
            val titles = arrayListOf<String>()
            val images = arrayListOf<String>()
            for (bannerBean in bannerList) {
                titles.add(bannerBean.title)
                images.add(bannerBean.imagePath)
            }
            banner.run {
                setBannerTitles(titles)
                setImages(images)
                start()
            }
        }
    }

    override fun onRequestArticleSuccess(articleList: ArticleBean) {
        if (pageNum++ == 0) {
            mStateView.showSuccess()
            mAdapter.setNewData(articleList.datas)
        } else {
            mAdapter.addData(articleList.datas)
            mRefreshLayout.finishLoadMore(true)
        }
    }

    override fun onCollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = true
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity,R.string.collect_success)
    }

    override fun onUncollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = false
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity,R.string.uncollect_success)
    }

    override fun notLogin() {
        AlertDialog.Builder(mActivity)
            .setTitle(R.string.dialog_hit)
            .setMessage(R.string.dialog_login_hint)
            .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, i: Int ->
                LoginActivity.start(mActivity)
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAccountEvent(event: LoginBean) {
        mAdapter.notifyDataSetChanged()//刷新界面数据
    }

    private fun onOriginClick(title: String) {
        var position = 0
        when (title) {
            "项目" -> {
                position = 1
            }
            "公众号" -> {
                position = 4
            }
        }
        (mActivity as? MainActivity)?.run {
            onPositionChange(position)
        }
    }

    private fun onStarClick(dataItem: ArticleDataItem,position: Int) {
        if (dataItem.collect) {
            mPresenter.requestUncollect(dataItem.id,position)
        } else {
            mPresenter.requestCollect(dataItem.id,position)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}