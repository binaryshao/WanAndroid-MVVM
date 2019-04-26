package com.sbingo.wanandroid_mvvm.data.http

import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.model.Page
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author: Sbingo666
 * Date:   2019/4/3
 */
interface WanApi {

    /**
     * 首页文章列表
     */
    @GET("article/list/{pageNo}/json")
    fun getArticles(@Path("pageNo") pageNo: Int): Observable<HttpResponse<Page<Article>>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    fun getWXChapters(): Observable<HttpResponse<List<Chapter>>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{pageNo}/json")
    fun getWXArticles(@Path("id") id: Int, @Path("pageNo") pageNo: Int): Observable<HttpResponse<Page<Article>>>

    /**
     * 项目类目列表
     */
    @GET("project/tree/json")
    fun getProjects(): Observable<HttpResponse<List<Chapter>>>

    /**
     * 项目文章列表
     */
    @GET("project/list/{pageNo}/json")
    fun getProjectArticles(@Path("pageNo") pageNo: Int, @Query("cid") cid: Int): Observable<HttpResponse<Page<Article>>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    fun getProjectArticles(): Observable<HttpResponse<List<Navigation>>>

    /**
     * 知识体系
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Observable<HttpResponse<List<Chapter>>>

    /**
     * 知识体系文章列表
     */
    @GET("article/list/{pageNo}/json")
    fun getKnowledgeArticles(@Path("pageNo") pageNo: Int, @Query("cid") cid: Int): Observable<HttpResponse<Page<Article>>>

}
