package com.sbingo.wanandroid_mvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingAdapter
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.ui.activity.WebActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationWebsiteAdapter(retryCallback: () -> Unit) : BasePagingAdapter<Navigation>(diffCallback, retryCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Navigation>() {
            override fun areContentsTheSame(oldItem: Navigation, newItem: Navigation): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Navigation, newItem: Navigation): Boolean =
                oldItem.cid == newItem.cid
        }
    }

    override fun getItemLayout() = R.layout.navigation_website

    override fun bind(holder: ViewHolder, item: Navigation, position: Int) {
        holder.run {
            setText(R.id.chapter, item.name)
            val flowLayout = getView(R.id.websites) as TagFlowLayout
            flowLayout.run {
                adapter = object : TagAdapter<Article>(item.articles) {
                    override fun getView(parent: FlowLayout?, position: Int, article: Article?): View? {
                        val tv = LayoutInflater.from(parent?.context).inflate(
                            R.layout.navigation_website_tv,
                            flowLayout,
                            false
                        ) as TextView
                        article ?: return null
                        tv.text = article.title
                        return tv
                    }
                }
                setOnTagClickListener { _, position, _ ->
                    val article = item.articles[position]
                    Intent(context, WebActivity::class.java).run {
                        putExtra(Constants.WEB_TITLE, article.title)
                        putExtra(Constants.WEB_URL, article.link)
                        context.startActivity(this)
                    }
                    true
                }
            }
        }
    }
}

