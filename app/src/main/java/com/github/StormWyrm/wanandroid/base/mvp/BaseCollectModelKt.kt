package com.github.StormWyrm.wanandroid.base.mvp

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import io.reactivex.Observable

open class BaseCollectModelKt : BaseModelKt(),ICollectModel{
    override fun requestCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofitHelper.instace.collectArticle(id)
    }

    override fun requestUncollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofitHelper.instace.uncollectArticle(id)
    }

}