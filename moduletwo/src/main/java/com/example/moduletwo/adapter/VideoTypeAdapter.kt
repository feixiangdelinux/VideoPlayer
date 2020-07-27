package com.example.moduletwo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.moduletwo.R

/**
 * @author : C4_雍和
 * 描述 :视频类型列表的适配器
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-27 上午10:45
 */
class VideoTypeAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_room_list) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.room_tv_name, item)
    }
}