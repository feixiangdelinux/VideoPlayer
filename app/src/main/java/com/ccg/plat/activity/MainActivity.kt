package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.jpush.android.api.JPushInterface
import com.ccg.plat.Const
import com.ccg.plat.R
import com.ccg.plat.entity.NavigationItem
import com.ccg.plat.ui.theme.ColorPrimary
import com.ccg.plat.ui.theme.TextColor2
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.ccg.plat.util.copyStr

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/18 10:17
 */
class MainActivity : ComponentActivity() {
    val context = this
    val mainBottomButtonDatas by lazy { mutableStateListOf<NavigationItem>() }
    var currentNavigationIndex = mutableStateOf(0)
    var isShowfdsa = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBottomButtonDatas.add(NavigationItem(title = "首页", selectedId = R.mipmap.main_shouye_light, unSelectedId = R.mipmap.main_shouye_dark))
        mainBottomButtonDatas.add(NavigationItem(title = "我的", selectedId = R.mipmap.main_personal_light, unSelectedId = R.mipmap.main_personal_dark))
        setContent {
            VideoPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomNavigation(backgroundColor = Color.White) {
                        mainBottomButtonDatas.forEachIndexed { index, navigationItem ->
                            BottomNavigationItem(selected = currentNavigationIndex.value == index, onClick = {
                                currentNavigationIndex.value = index
                            }, icon = {
                                Image(
                                    painter = painterResource(
                                        id = if (currentNavigationIndex.value == index) {
                                            navigationItem.selectedId
                                        } else {
                                            navigationItem.unSelectedId
                                        }
                                    ), contentDescription = null
                                )
                            }, label = {
                                val col = if (currentNavigationIndex.value == index) {
                                    ColorPrimary
                                } else {
                                    TextColor2
                                }
                                Text(text = navigationItem.title, color = col, fontSize = 10.sp)
                            })
                        }
                    }
                }) {
                    when (currentNavigationIndex.value) {
                        0 -> {
                            MainUI()
                        }
                        1 -> {
                            MyUI()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyUI() {
        Column(modifier = Modifier.fillMaxSize().padding(start = 10.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {
                var isShow by remember { mutableStateOf("免费用户") }
                LaunchedEffect(Const.IS_VIP) {
                    isShow = if (Const.IS_VIP) {
                        "VIP"
                    } else {
                        "免费用户"
                    }
                }
                Text(
                    text = "您当前的状态:   $isShow"
                )
            }
            Text(text = "免费用户每天能看20条视频,VIP用户无限制")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "购买VIP流程:微信或QQ任选")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "1点击QQ群号,就能复制群号,然后去加群")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "2点击微信号,就能复制,然后去微信里添加好友")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "开通VIP需要提供用户名,点击用户名就能复制")
            Row(modifier = Modifier.fillMaxWidth().height(40.dp).clickable {
                Toast.makeText(context, "QQ群号复制成功", Toast.LENGTH_SHORT).show()
                context.copyStr("463208733")
            }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "QQ群号:")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "463208733")
            }
            Row(modifier = Modifier.fillMaxWidth().height(40.dp).clickable {
                Toast.makeText(context, "微信号复制成功", Toast.LENGTH_SHORT).show()
                context.copyStr("957493412")
            }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "微信号:")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "957493412")
            }
            Row(modifier = Modifier.fillMaxWidth().height(40.dp).clickable {
                Toast.makeText(context, "用户名复制成功", Toast.LENGTH_SHORT).show()
                context.copyStr(JPushInterface.getRegistrationID(context))
            }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "用户名:")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "" + JPushInterface.getRegistrationID(context))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "注意: 软件卸载VIP就会消失,在想开通就需要在花钱,切记没事别卸载")
            Box(modifier = Modifier.fillMaxWidth().height(12.dp).clickable(interactionSource = MutableInteractionSource(), indication = null) {
                if (isShowfdsa > 0) {
                    isShowfdsa -= 1
                } else {
                    startActivity(Intent(context, AdminActivity::class.java))
                }
            }) {

            }
        }
    }

    @Composable
    fun MainUI() {
        LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 10.dp).shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp)).padding(vertical = 10.dp).clickable {
                    startActivity(Intent(context, VideoOneActivity::class.java))
                }, contentAlignment = Alignment.Center) {
                    Text(text = "小黄人播放器")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}



