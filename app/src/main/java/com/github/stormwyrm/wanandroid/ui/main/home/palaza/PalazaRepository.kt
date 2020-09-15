package com.github.stormwyrm.wanandroid.ui.main.home.palaza

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class PalazaRepository {

    suspend fun getPalazaArticleList(page: Int) =
        RetrofitClient.getApiService().getPalazaArticleList(page).apiData()

}