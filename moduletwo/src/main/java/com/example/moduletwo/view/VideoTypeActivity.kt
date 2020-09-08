package com.example.moduletwo.view

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ccg.libbase.BaseActivityB
import com.example.moduletwo.R
import com.example.moduletwo.adapter.VideoTypeAdapter
import com.example.moduletwo.databinding.ActivityVideoListBinding
import com.example.moduletwo.viewmodel.VideoTypeViewModel
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :视频类型列表
 * 主要功能 :显示房间内所包含的类型
 * 维护人员 : C4_雍和
 * date : 20-7-27 上午10:40
 */
class VideoTypeActivity : BaseActivityB<VideoTypeViewModel>() {
    override fun providerVMClass(): Class<VideoTypeViewModel>? =
        VideoTypeViewModel::class.java

    private lateinit var binding: ActivityVideoListBinding
    private var url = ""
    private var adapter = VideoTypeAdapter()
    private val context = this
    override fun initView() {
        binding = DataBindingUtil.setContentView<ActivityVideoListBinding>(
            this,
            R.layout.activity_video_list
        )
    }

    override fun initData() {
        intent.getStringExtra("json")?.run {
            url = this
        }
        binding.recyclerView.adapter = adapter
        getData()
    }

    override fun setListener() {
        adapter.setOnItemClickListener { _, _, position ->
            viewModel.uiData.value?.run {
                val intent = Intent(context, VideoListActivity::class.java)
                intent.putExtra("json", viewModel.allData[position])
                startActivity(intent)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            uiData.observe(context, Observer {
                adapter.setNewData(it)
            })
            errMsg.observe(context, Observer {
                Timber.e("加载失败$it")
            })
        }
    }

    override fun getData() {
        super.getData()
        viewModel.initData(url)
    }
}
