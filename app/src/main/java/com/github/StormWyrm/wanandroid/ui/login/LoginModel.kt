package com.github.StormWyrm.wanandroid.ui.login

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.LoginBean
import io.reactivex.Observable

class LoginModel : BaseModelKt(), LoginContract.Model {
    override fun requestLogin(map: Map<String, String>): Observable<BaseResponse<LoginBean>> {
        return WanAndroidRetrofitHelper.instace.login(map)
    }
}
