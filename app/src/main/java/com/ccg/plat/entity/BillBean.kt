package com.ccg.plat.entity

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/16 13:45
 */
data class BillBean(
    //sendTime(开通时间)
    val t: String,
    //accountNumber(开通账号)
    val n: String,
    //isOpen(是开通还是关闭vip)
    val o: Boolean,
)
