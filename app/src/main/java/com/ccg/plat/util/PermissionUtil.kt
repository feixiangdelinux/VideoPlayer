package com.ccg.plat.util

import android.Manifest
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/3/2 10:41
 */
object PermissionUtil {

    /**
     * 检查用户是否授权了存储权限
     * @param onClick Function0<Unit>
     */
    fun checkPermission(onClick: () -> Unit) {
        if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            onClick()
        } else {
            PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: List<String>) {
                    onClick()
                }
                override fun onDenied(deniedForever: List<String>, denied: List<String>) {
                    ToastUtils.showShort("请开启权限")
                }
            }).request()
        }
    }
}