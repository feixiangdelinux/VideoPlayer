package com.ccg.moduleone

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.billy.cc.core.component.CCUtil
import com.google.gson.GsonBuilder
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class VideoPlayActivity : Activity() {
    private lateinit var videoPlayer: StandardGSYVideoPlayer
    private lateinit var orientationUtils: OrientationUtils
    private val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_video_play)
        videoPlayer = findViewById<StandardGSYVideoPlayer>(R.id.detail_player)
        //设置旋转
        orientationUtils = OrientationUtils(this, videoPlayer)
        val json = CCUtil.getNavigateParam(
            this,
            "json",
            ""
        )
        val tempData = GsonBuilder().create().fromJson<VideoBean>(json, VideoBean::class.java)
        videoPlayer.setUp(tempData.vUrl, true, tempData.name)
        //增加title
        videoPlayer.titleTextView.visibility = View.VISIBLE
        //设置返回键
        videoPlayer.backButton.visibility = View.VISIBLE
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.fullscreenButton
            .setOnClickListener {
                //设置全屏
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                hideBottomMenu()
                orientationUtils.resolveByClick()
            }
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true)
        //设置返回按键功能
        videoPlayer.backButton.setOnClickListener { onBackPressed() }
        videoPlayer.startPlayLogic()

    }

    /**
     * 隐藏虚拟案件
     */
    private fun hideBottomMenu() {
        val v = this.window.decorView
        if (Build.VERSION.SDK_INT in 12..18) {
            v.systemUiVisibility = View.GONE
        } else {
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            v.systemUiVisibility = uiOptions
        }
    }


    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}
