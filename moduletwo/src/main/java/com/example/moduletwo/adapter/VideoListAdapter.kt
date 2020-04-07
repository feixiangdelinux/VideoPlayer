package com.example.moduletwo.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.moduletwo.R
import com.example.moduletwo.entity.VideoBean

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-5 下午2:34
 */
class VideoListAdapter  : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_video_list) {
    override fun convert(helper: BaseViewHolder, item:VideoBean) {
        helper.setText(R.id.video_list_tv_name,item.name)
        Glide.with(context).load(item.getpUrl())
            .into(helper.getView<ImageView>(R.id.video_list_iv_ic))
    }
}