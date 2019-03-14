package com.github.StormWyrm.wanandroid.ui.register

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.LoginBean
import com.github.StormWyrm.wanandroid.bean.RegisterBean
import io.reactivex.Observable

class RegisterModel : BaseModelKt(), RegisterContract.Model {
    override fun requestRegister(map: Map<String, String>): Observable<BaseResponse<RegisterBean>> {
        return WanAndroidRetrofitHelper.instace.register(map)
    }
}
