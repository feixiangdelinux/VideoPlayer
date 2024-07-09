package com.ccg.plat.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/27 17:25
 */

/**
 * 复制字符串到剪切版
 * @receiver Context
 * @param str String
 */
fun Context.copyStr(str: String) {
    val clipboardManagerOne = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManagerOne.setPrimaryClip(ClipData.newPlainText(null, str))
}

