package com.github.StormWyrm.wanandroid.ui.chapter

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean
import io.reactivex.Observable

class ChapterModel : BaseModelKt(), ChapterContract.Model {
    override fun requestChapterList(): Observable<BaseResponse<List<ChapterBean>>> {
        return WanAndroidRetrofitHelper.instace.chapter()
    }
}