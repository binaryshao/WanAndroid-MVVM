package com.sbingo.wanandroid_mvvm.base.paging

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.RequestState
import com.sbingo.wanandroid_mvvm.model.Banner
import com.sbingo.wanandroid_mvvm.ui.activity.WebActivity
import com.sbingo.wanandroid_mvvm.utils.ImageLoader
import io.reactivex.Observable

/**
 * Author: Sbingo666
 * Date:   2019/4/12
 */
abstract class BasePagingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>, private val retryCallback: () -> Unit) :
    PagedListAdapter<T, BasePagingAdapter.ViewHolder>(diffCallback) {

    private val TYPE_ITEM = 0
    private val TYPE_BANNER = 1
    private val TYPE_FOOTER = 2
    private var requestState: RequestState<Any>? = null
    lateinit var context: Context
    private var hasBannerView = false
    private var bannerData: List<Banner>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return when (viewType) {
            TYPE_BANNER -> BannerViewHolder.create(parent, bannerData!!)
            TYPE_ITEM -> ViewHolder(LayoutInflater.from(context).inflate(getItemLayout(), parent, false))
            TYPE_FOOTER -> FooterViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("未知 view type = $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_BANNER -> {
                (holder as BannerViewHolder).bind()
            }
            TYPE_ITEM -> {
                val p = if (hasBannerView) position - 1 else position
                bind(holder, getItem(p)!!, p)
            }
            TYPE_FOOTER -> (holder as FooterViewHolder).bindTo(requestState)
        }
    }

    open class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val map = mutableMapOf<Int, View>()

        fun getView(id: Int): View? {
            var view = map[id]
            if (view == null) {
                view = this.view.findViewById(id)
                map[id] = view
            }
            return view
        }

        fun setText(id: Int, string: String?) {
            val textView = getView(id)
            if (textView is TextView) {
                textView.text = string
            }
        }

        fun toVisibility(id: Int, constraint: Boolean) {
            getView(id)?.visibility = if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    class FooterViewHolder(view: View, private val retryCallback: () -> Unit) : ViewHolder(view) {

        init {
            getView(R.id.retry_button)?.setOnClickListener {
                retryCallback()
            }
        }

        fun bindTo(requestState: RequestState<Any>?) {
            toVisibility(R.id.progress_bar, requestState!!.isLoading())
            toVisibility(R.id.retry_button, requestState.isError())
            toVisibility(R.id.msg, requestState.isLoading())
            setText(R.id.msg, "加载中...")
        }

        companion object {
            fun create(parent: ViewGroup, retryCallback: () -> Unit): FooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.footer_item, parent, false)
                return FooterViewHolder(view, retryCallback)
            }
        }
    }

    class BannerViewHolder(view: View, private val bannerData: List<Banner>) : ViewHolder(view) {
        fun bind() {
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
            fun create(parent: ViewGroup, bannerData: List<Banner>): BannerViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.banner_layout, parent, false)
                return BannerViewHolder(view, bannerData)
            }
        }
    }

    private fun hasFooter() =
        if (requestState == null)
            false
        else {
            !requestState?.isSuccess()!!
        }

    override fun getItemViewType(position: Int): Int {
        return if (hasBannerView && position == 0) {
            TYPE_BANNER
        } else if (hasFooter() && position == itemCount - 1) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        val i = super.getItemCount() + if (hasFooter()) 1 else 0
        return i + if (hasBannerView) 1 else 0
    }

    fun setRequestState(newRequestState: RequestState<Any>) {
        val previousState = this.requestState
        val hadExtraRow = hasFooter()
        this.requestState = newRequestState
        val hasExtraRow = hasFooter()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newRequestState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun setBannerData(data: List<Banner>?) {
        hasBannerView = data != null && data.isNotEmpty()
        bannerData = data
        notifyDataSetChanged()
    }

    abstract fun getItemLayout(): Int

    abstract fun bind(holder: ViewHolder, item: T, position: Int)

}