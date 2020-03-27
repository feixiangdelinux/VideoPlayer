package com.ccg.videoplayer.adapter

import com.ccg.videoplayer.R
import com.ccg.videoplayer.entity.RoomBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午4:15
 */
class MainAdapter : BaseQuickAdapter<RoomBean.DataBean, BaseViewHolder>(R.layout.item_man_room) {
    override fun convert(helper: BaseViewHolder, item: RoomBean.DataBean) {
        helper.setText(R.id.room_tv_name,item.roomName)
    }
}