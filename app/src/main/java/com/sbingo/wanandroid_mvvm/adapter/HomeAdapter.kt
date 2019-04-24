package com.sbingo.wanandroid_mvvm.adapter

import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingAdapter
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.utils.ImageLoader

/**
 * Author: Sbingo666
 * Date:   2019/4/18
 */
class HomeAdapter(retryCallback: () -> Unit) : BasePagingAdapter<Article>(diffCallback, retryCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun getItemLayout() = R.layout.home_item

    override fun bind(holder: ViewHolder, item: Article, position: Int) {
        holder.run {
            toVisibility(R.id.fresh, item.fresh)
            toVisibility(R.id.thumbnail, item.envelopePic.isNotBlank())
            toVisibility(R.id.desc, item.desc.isNotBlank())
            setText(R.id.author, item.author)
            setText(R.id.date, item.niceDate)
            setText(R.id.title, HtmlCompat.fromHtml(item.title, FROM_HTML_MODE_LEGACY).toString())
            setText(
                R.id.chapter, when {
                    item.superChapterName.isNotBlank() and item.chapterName.isNotBlank() ->
                        "${item.superChapterName} / ${item.chapterName}"
                    item.superChapterName.isNotBlank() -> item.superChapterName
                    item.chapterName.isNotBlank() -> item.chapterName
                    else -> ""
                }
            )
            setText(R.id.desc, item.desc)
        }
        if (item.envelopePic.isNotBlank()) {
            context.run { ImageLoader.load(context, item.envelopePic, holder.getView(R.id.thumbnail) as ImageView) }
        }
    }
}