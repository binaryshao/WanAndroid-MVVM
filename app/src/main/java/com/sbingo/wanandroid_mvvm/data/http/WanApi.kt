package com.sbingo.wanandroid_mvvm.data.http

import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.model.Page
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

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
    fun getWXArticles(@Path("id") id: Int, @Path("pageNo") pageNo: Int): Observable<HttpResponse<ArticlePages>>
}
