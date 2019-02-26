package com.github.StormWyrm.wanandroid.ui.tree

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import io.reactivex.Observable

class TreeModel : TreeContract.Modle, BaseModelKt() {
    override fun requestTreeList(): Observable<BaseResponse<List<TreeBean>>> {
        return WanAndroidRetrofitHelper.instace.treeList()
    }
}