package com.github.StormWyrm.wanandroid.ui.chapter

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean
import io.reactivex.Observable

interface ChapterContract {
    interface Model : IModel {
        fun requestChapterList(): Observable<BaseResponse<List<ChapterBean>>>
    }

    interface View : IListView<Presenter> {
        fun onRequestChapterListSuccess(datas: List<ChapterBean>)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestChapterList()
    }
}