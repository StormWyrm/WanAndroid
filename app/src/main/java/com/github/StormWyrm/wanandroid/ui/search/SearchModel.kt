package com.github.StormWyrm.wanandroid.ui.search

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import io.reactivex.Observable

class SearchModel : SearchContract.Model, BaseModelKt() {
    override fun requestHotKey(): Observable<BaseResponse<List<HotKeyBean>>> {
        return WanAndroidRetrofitHelper.instace.hotKey()
    }

}
