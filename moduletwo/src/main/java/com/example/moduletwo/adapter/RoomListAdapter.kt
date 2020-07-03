package com.example.moduletwo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.moduletwo.R
import com.example.moduletwo.entity.RoomBean

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-7-2 下午5:07
 */
class RoomListAdapter :
    BaseQuickAdapter<RoomBean.DataBean, BaseViewHolder>(R.layout.item_room_list) {
    override fun convert(helper: BaseViewHolder, item: RoomBean.DataBean) {
        helper.setText(R.id.room_tv_name, item.roomName)
    }
}