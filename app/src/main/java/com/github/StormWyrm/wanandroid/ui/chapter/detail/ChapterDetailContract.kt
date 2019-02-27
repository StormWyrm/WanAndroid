package com.github.StormWyrm.wanandroid.ui.chapter.detail

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterDetailDataItem
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean
import io.reactivex.Observable

interface ChapterDetailContract {


    interface Model : IModel {
        fun requestChapterDetailArticle(chapterId : Int,pageNum : Int): Observable<BaseResponse<ChapterDetailBean>>

    }

    interface Presenter : IPresenter<View, Model> {
        fun requestChapterDetailArticle(chapterId : Int,pageNum : Int)

    }

    interface View : IListView<Presenter> {
        fun onRequestChapterDetailArticleSuccess(datas: List<ChapterDetailDataItem>)
    }
}
