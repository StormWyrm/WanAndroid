package com.github.StormWyrm.wanandroid.ui.collection

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

class CollectionModel : BaseModelKt(), CollectionContract.Model {
    override fun requestCollectionList(pageNum: Int): Observable<BaseResponse<ArticleBean>> {
        return WanAndroidRetrofitHelper
            .instace
            .collectArticleList(pageNum)
    }

    override fun requestRemoveMyCollection(id: Int, originId: Int):Observable<BaseResponse<String>> {
        return WanAndroidRetrofitHelper
            .instace
            .removeMyCollection(id,originId)
    }

}
