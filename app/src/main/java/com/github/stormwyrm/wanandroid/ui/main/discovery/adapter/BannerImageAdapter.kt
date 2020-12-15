package com.github.stormwyrm.wanandroid.ui.main.discovery.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.stormwyrm.wanandroid.bean.Banner
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter(
    val datas: List<Banner>
) : BannerAdapter<Banner, BaseViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {

        return BaseViewHolder(ImageView(parent?.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
    }

    override fun onBindView(holder: BaseViewHolder?, data: Banner?, position: Int, size: Int) {
        data?.run {
            Glide.with(holder!!.itemView)
                .load(imagePath)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.itemView as ImageView)
        }
    }

}
