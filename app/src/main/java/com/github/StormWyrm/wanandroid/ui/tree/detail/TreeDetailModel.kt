package com.github.StormWyrm.wanandroid.ui.tree.detail

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean
import io.reactivex.Observable

class TreeDetailModel : TreeDetailContract.Model, BaseModelKt() {
    override fun requestTreeDetailList(pageNum: Int, cid: Int): Observable<BaseResponse<TreeDetailBean>> {
        return WanAndroidRetrofitHelper.instace.treeDetailList(pageNum, cid)
    }

}
