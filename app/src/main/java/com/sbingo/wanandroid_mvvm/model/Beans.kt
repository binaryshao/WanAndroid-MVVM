package com.sbingo.wanandroid_mvvm.model

import java.io.Serializable


/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
data class Page<T>(
    var curPage: Int,
    var datas: MutableList<T>,
    var offset: Int,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var over: Boolean
)

data class Article(
    var apkLink: String,
    var author: String,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int,
    var tags: List<Tag>,
    var isTop: Boolean,
    var bannerData: List<Banner>
)

data class Tag(
    var name: String,
    var url: String
)

data class Chapter(
    var children: List<Chapter>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
) : Serializable

data class Navigation(
    var articles: List<Article>,
    var cid: Int,
    var name: String
)

data class Banner(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)