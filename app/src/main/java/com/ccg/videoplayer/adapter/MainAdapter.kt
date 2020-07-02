package com.ccg.videoplayer.adapter

import com.ccg.videoplayer.R
import com.ccg.videoplayer.entity.AbaseBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午4:15
 */
class MainAdapter : BaseQuickAdapter<AbaseBean, BaseViewHolder>(R.layout.item_man_fun) {
    override fun convert(helper: BaseViewHolder, item: AbaseBean) {
        helper.setText(R.id.fun_tv_name,item.name)
    }

}