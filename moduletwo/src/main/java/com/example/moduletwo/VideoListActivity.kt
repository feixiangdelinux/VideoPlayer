package com.example.moduletwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.billy.cc.core.component.CCUtil
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :视频列表页面
 * 主要功能 :以列表形势显示所有视频
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:27
 */
class VideoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val json = CCUtil.getNavigateParam(
            this,
            "json",
            ""
        )
        Timber.e(json)
    }
}
