package com.sbingo.wanandroid_mvvm.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingAdapter
import com.sbingo.wanandroid_mvvm.model.Navigation

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationChapterAdapter(retryCallback: () -> Unit, private val listener: OnItemClickListener) :
    BasePagingAdapter<Navigation>(diffCallback, retryCallback) {

    private var positionChecked: Int = 0

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Navigation>() {
            override fun areContentsTheSame(oldItem: Navigation, newItem: Navigation): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Navigation, newItem: Navigation): Boolean =
                oldItem.cid == newItem.cid
        }
    }

    override fun getItemLayout() = R.layout.navigation_tv

    override fun bind(holder: ViewHolder, item: Navigation, position: Int) {
        holder.run {
            setText(R.id.chapter, item.name)
        }
        holder.itemView.run {
            setOnClickListener {
                listener.onItemClicked(position)
                setCheckedPosition(position)
            }
            setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (position == positionChecked) {
                        R.color.colorPrimaryLight
                    } else {
                        R.color.White
                    }
                )
            )
        }
    }

    fun setCheckedPosition(position: Int) {
        positionChecked = position
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int) {
        }
    }
}