package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import io.reactivex.Observable

class CategoryModel : BaseModelKt(), CategoryContract.Model {
    override fun requestProjectAricle(categoryId: Int, pageNum: Int): Observable<BaseResponse<ProjectBean>> {
        return WanAndroidRetrofitHelper.instace.projectList(categoryId, pageNum)
    }

}