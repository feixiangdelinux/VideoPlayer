package com.ccg.plat.entity

import androidx.annotation.DrawableRes

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/9/21 10:00
 */
data class NavigationItem(
    val title: String,
    @DrawableRes var selectedId: Int,
    @DrawableRes var unSelectedId: Int,
)
