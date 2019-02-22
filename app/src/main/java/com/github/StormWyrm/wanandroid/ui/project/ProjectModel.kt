package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean
import io.reactivex.Observable

class ProjectModel : BaseModelKt(), ProjectContract.Model {
    override fun getProjectCategory(): Observable<BaseResponse<List<ProjectCategoryBean>>> =
        WanAndroidRetrofitHelper.instace.projectCategory()
}