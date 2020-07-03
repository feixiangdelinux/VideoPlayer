package com.example.moduletwo

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent
import com.example.moduletwo.view.RoomListActivity

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午4:49
 */
class ComponentUtil : IComponent {
    override fun getName(): String {
        //指定组件的名称
        return "ComponentTwo"
    }

    override fun onCall(cc: CC): Boolean {
        return when (cc.actionName) {
            "RoomListActivity" -> {
                CCUtil.navigateTo(cc, RoomListActivity::class.java)
                //返回处理结果给调用方
                CC.sendCCResult(cc.callId, CCResult.success())
                //同步方式实现（在return之前听过CC.sendCCResult()返回组件调用结果），return false
                false
            }
            else -> {
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.callId, CCResult.errorUnsupportedActionName())
                false
            }
        }
    }
}
