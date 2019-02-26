package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import io.reactivex.Observable

class ProjectCategoryModel : BaseModelKt(), ProjectCategoryContract.Model {
    override fun requestProjectAricle(pageNum: Int,categoryId: Int): Observable<BaseResponse<ProjectBean>> {
        return WanAndroidRetrofitHelper.instace.projectList( pageNum,categoryId)
    }

}