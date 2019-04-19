package com.sbingo.wanandroid_mvvm.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sbingo.wanandroid_mvvm.R

/**
 * Author: Sbingo666
 * Date:   2019/4/18
 */
object ImageLoader {

    fun load(context: Context?, url: String?, iv: ImageView?) {
        iv?.apply {
            Glide.with(context!!).clear(iv)
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
                .error(R.drawable.pic_error)
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(iv)
        }
    }

}