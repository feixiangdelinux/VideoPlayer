package com.ccg.plat.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ccg.plat.Const
import com.ccg.plat.entity.RoomBean
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2023/2/9 12:44
 */
class CollectionActivity : ComponentActivity() {
    val context = this
    val kv = MMKV.defaultMMKV()
    val collectionData by lazy { mutableStateListOf<RoomBean>() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val json = kv.decodeString("collection_key")
            if (json.isNullOrEmpty()) {
            } else {
                if (Const.finalVideoList.isNotEmpty()) {
                    Const.finalVideoList.clear()
                }
                Const.finalVideoList = GsonBuilder().create().fromJson<MutableList<RoomBean>>(json, object : TypeToken<MutableList<RoomBean>>() {}.type)
                collectionData.addAll(Const.finalVideoList)
            }
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    if (Const.IS_VIP) {
                        if (collectionData.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                item {
                                    Text(text = "电影暂停就会显示收藏按钮\n点击收藏按钮即可收藏", modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 20.sp)
                                }

                                items(count = collectionData.size) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()) {
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .clickable {
                                                val intent = Intent(context, SimplePlayerActivity::class.java)
                                                intent.putExtra("index", it)
                                                startActivity(intent)
                                            }, verticalAlignment = Alignment.CenterVertically) {
                                            Spacer(modifier = Modifier.width(10.dp))
                                            AsyncImage(model = collectionData[it].pUrl, contentDescription = null, modifier = Modifier
                                                .width(120.dp)
                                                .wrapContentHeight()
                                                .clip(shape = RoundedCornerShape(10)))
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = collectionData[it].name, modifier = Modifier
                                                .weight(1f)
                                                .wrapContentHeight())
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Divider(thickness = 1.dp)
                                    }
                                }

                            }
                        }
                    } else {
                        Text(text = "你不是VIP,无法使用收藏夹功能", modifier = Modifier.padding(start = 20.dp, top = 15.dp, bottom = 15.dp), fontSize = 20.sp)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Const.finalVideoList.isNotEmpty()) {
            Const.finalVideoList.clear()
        }
    }
}