package com.github.StormWyrm.wanandroid.apis

import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean
import com.github.StormWyrm.wanandroid.bean.query.QueryBean
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean
import io.reactivex.Observable
import retrofit2.http.*

interface WanAndroidApis {

    /**
     * 登录
     */
    @POST("user/login")
    fun login(@QueryMap param: Map<String, String>)

    /**
     * 注册
     */
    @POST("user/register")
    fun register(@QueryMap param: Map<String, String>)


    /**
     * 退出
     */
    @POST("user/logout/json")
    fun logout()


    /**
     * 首页banner
     */
    @GET("banner/json")
    fun banner(): Observable<BaseResponse<List<BannerBean>>>


    /**
     * 首页文章列表
     */

    @GET("article/list/{pageNum}/json")
    fun articleList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleBean>>


    /**
     * 项目类别
     */

    @GET("project/tree/json")
    fun projectCategory(): Observable<BaseResponse<List<ProjectCategoryBean>>>


    /**
     * 某个项目类别下项目列表
     */
    @GET("project/list/{pageNum}/json")
    fun projectList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int): Observable<BaseResponse<ProjectBean>>


    /**
     * 体系数据
     */
    @GET("tree/json")
    fun treeList(): Observable<BaseResponse<TreeBean>>


    /**
     * 体系文章列表
     */
    @GET("article/list/{pageNum}/json")
    fun treeDetailList(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int): Observable<BaseResponse<TreeDetailBean>>


    /**
     * 导航数据
     */
    @GET("navi/json")
    fun naviList(): Observable<BaseResponse<NaviBean>>


    /**
     * 微信公众号列表
     */
    @GET("wxarticle/chapters/json")
    fun chapter(): Observable<BaseResponse<List<ChapterBean>>>

    /**
     * 微信公众号文章列表
     */
    @GET("wxarticle/list/{chapterId}/{pageNum}/json")
    fun chapterArticleList(@Path("chapterId") chapterId: Int, @Path("pageNum") pageNum: Int): Observable<BaseResponse<ChapterDetailBean>>

    /**
     * 微信公众号文章搜索
     */
    @GET("wxarticle/list/{chapterId}/{pageNum}/json")
    fun queryChapterArticle(@Path("chapterId") chapterId: Int, @Path("pageNum") pageNum: Int, @Query("k") k: String): Observable<BaseResponse<ChapterDetailBean>>

    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{pageNum}/json")
    fun collectArticleList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleBean>>


    /**
     * 在文章列表取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun uncollectArticle(@Path("id") id: Int): Observable<BaseResponse<String>>

    /**
     * 在收藏列表取消收藏
     */
    @POST("lg/uncollect/{id}/json")
    fun cancelMyCollection(@Path("id") id: Int, @Query("originId") originId: Int): Observable<BaseResponse<String>>

    /**
     * 热搜词
     */
    @GET("hotkey/json")
    fun hotKey(): Observable<BaseResponse<List<HotKeyBean>>>


    /**
     * 搜索（支持多个关键词，用空格隔开）
     */
    @POST("article/query/{pageNum}/json")
    fun query(@Path("pageNum") pageNum: Int, @Query("k") k: String): Observable<BaseResponse<QueryBean>>


}