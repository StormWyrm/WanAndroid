package com.github.StormWyrm.wanandroid.ui.chapter.detail

import com.github.StormWyrm.wanandroid.base.mvp.*
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterDetailDataItem
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean
import io.reactivex.Observable

interface ChapterDetailContract {


    interface Model : ICollectModel {
        fun requestChapterDetailArticle(chapterId : Int,pageNum : Int): Observable<BaseResponse<ChapterDetailBean>>

    }

    interface Presenter : ICollectPresenter<View, Model> {
        fun requestChapterDetailArticle(chapterId : Int,pageNum : Int)

    }

    interface View : ICollectView<Presenter> {
        fun onRequestChapterDetailArticleSuccess(datas: List<ChapterDetailDataItem>)
    }
}
