package com.ccg.plat.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import com.ccg.plat.ui.theme.VideoPlayerTheme
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 2022/10/18 10:17
 */
class VideoPlayActivity : ComponentActivity() {
    val context = this
    private var name = ""
    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("url")?.run {
            url = this
        }
        intent.getStringExtra("name")?.run {
            name = this
        }
        ScreenUtils.setLandscape(context)
        ScreenUtils.setFullScreen(context)
        setContent {
            VideoPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AndroidView(factory = { context ->
                        return@AndroidView StandardGSYVideoPlayer(context)
                    }, modifier = Modifier.fillMaxSize(), update = { videoPlay ->
                        videoPlay.setUp(url, false, name)
                        videoPlay.fullscreenButton.setOnClickListener {
                            videoPlay.startWindowFullscreen(context, false, true)
                        }
                        videoPlay.backButton.setOnClickListener {
                            finish()
                        }
                        videoPlay.startPlayLogic()

                    })
                }
            }
        }
    }
}

