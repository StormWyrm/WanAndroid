package com.github.StormWyrm.wanandroid.ui.tree.detail

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseCollectModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean
import io.reactivex.Observable

class TreeDetailModel : BaseCollectModelKt(), TreeDetailContract.Model {
    override fun requestTreeDetailList(pageNum: Int, cid: Int): Observable<BaseResponse<TreeDetailBean>> {
        return WanAndroidRetrofitHelper.instace.treeDetailList(pageNum, cid)
    }

}
