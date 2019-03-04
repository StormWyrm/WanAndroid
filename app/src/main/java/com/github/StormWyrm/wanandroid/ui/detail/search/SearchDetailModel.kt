package com.github.StormWyrm.wanandroid.ui.detail.search

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.query.QueryBean
import io.reactivex.Observable

class SearchDetailModel : SearchDetailContract.Model, BaseModelKt() {
    override fun requestQueryKey(catory: Int, queryKey: String, pageNum: Int): Observable<BaseResponse<QueryBean>> {
        return when (catory) {
            SearchDetailActivity.AUTHOR -> WanAndroidRetrofitHelper.instace.queryAuthor(pageNum, queryKey)
            else -> {
                WanAndroidRetrofitHelper.instace.query(pageNum, queryKey)
            }
        }
    }
}
