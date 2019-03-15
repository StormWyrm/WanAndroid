package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseCollectModelKt
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

class HomeModel : BaseCollectModelKt(), HomeContract.Model {
    override fun reqeustBanner(): Observable<BaseResponse<List<BannerBean>>> {
        return WanAndroidRetrofitHelper
            .instace
            .banner()
    }

    override fun requestArticleList(pageNum: Int): Observable<BaseResponse<ArticleBean>> {
        return WanAndroidRetrofitHelper
            .instace
            .articleList(pageNum)
    }
}