package com.github.stormwyrm.wanandroid.ui.main.home.wechat

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class WechatRepository {

    suspend fun getWechatCategory() = RetrofitClient.getApiService().getWechatCategory().apiData()

    suspend fun getWechatList(cid: Int, page: Int) =
        RetrofitClient.getApiService().getWechatArticleList(cid, page).apiData()

}