package com.sbingo.wanandroid_mvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import cn.bingoogolapple.bgabanner.BGABanner
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingAdapter
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Banner
import com.sbingo.wanandroid_mvvm.ui.activity.WebActivity
import com.sbingo.wanandroid_mvvm.utils.ImageLoader
import io.reactivex.Observable

/**
 * Author: Sbingo666
 * Date:   2019/4/18
 */
class HomeAdapter(retryCallback: () -> Unit) : BasePagingAdapter<Article>(diffCallback, retryCallback) {

    private val TYPE_BANNER = 10

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
            toVisibility(R.id.on_top, item.isTop)
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
            context.let { ImageLoader.load(it, item.envelopePic, holder.getView(R.id.thumbnail) as ImageView) }
        }
        holder.itemView.setOnClickListener {
            Intent(context, WebActivity::class.java).run {
                putExtra(Constants.WEB_TITLE, item.title)
                putExtra(Constants.WEB_URL, item.link)
                context.startActivity(this)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && !getItem(position)?.bannerData.isNullOrEmpty()) {
            return TYPE_BANNER
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == TYPE_BANNER) {
            return BannerViewHolder.create(parent)
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER) {
            (holder as BannerViewHolder).bind(getItem(position)!!.bannerData)
        }
        super.onBindViewHolder(holder, position)
    }

    class BannerViewHolder(view: View) : ViewHolder(view) {
        fun bind(bannerData: List<Banner>) {
            val banner = getView(R.id.banner) as BGABanner
            banner.run {
                setAdapter { _, itemView, model, _ ->
                    ImageLoader.load(this.context, model as String?, itemView as ImageView?)
                }
                setDelegate { _, _, _, position ->
                    if (bannerData.isNotEmpty()) {
                        val item = bannerData[position]
                        Intent(context, WebActivity::class.java).run {
                            putExtra(Constants.WEB_TITLE, item.title)
                            putExtra(Constants.WEB_URL, item.url)
                            context.startActivity(this)
                        }
                    }
                }
                val imageList = ArrayList<String>()
                val titleList = ArrayList<String>()
                Observable.fromIterable(bannerData)
                    .subscribe { list ->
                        imageList.add(list.imagePath)
                        titleList.add(list.title)
                    }
                setAutoPlayAble(imageList.size > 1)
                setData(imageList, titleList)
            }
        }

        companion object {
            fun create(parent: ViewGroup): BannerViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.banner_layout, parent, false)
                return BannerViewHolder(view)
            }
        }
    }

}