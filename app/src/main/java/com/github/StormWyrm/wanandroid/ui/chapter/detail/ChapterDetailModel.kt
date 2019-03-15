package com.github.StormWyrm.wanandroid.ui.chapter.detail

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseCollectModelKt
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean
import io.reactivex.Observable

class ChapterDetailModel : BaseCollectModelKt() , ChapterDetailContract.Model {
    override fun requestChapterDetailArticle(
        chapterId: Int,
        pageNum: Int
    ): Observable<BaseResponse<ChapterDetailBean>> {
        return WanAndroidRetrofitHelper.instace.chapterArticleList(chapterId, pageNum)
    }

}