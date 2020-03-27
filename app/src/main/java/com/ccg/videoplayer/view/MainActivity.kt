package com.ccg.videoplayer.view

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ccg.libbase.BaseActivityB
import com.ccg.videoplayer.R
import com.ccg.videoplayer.adapter.MainAdapter
import com.ccg.videoplayer.databinding.ActivityMainBinding
import com.ccg.videoplayer.util.NavigationUtils
import com.ccg.videoplayer.viewmodel.MainViewModel
import com.google.gson.GsonBuilder
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :主页面
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:27
 */
class MainActivity : BaseActivityB<MainViewModel>() {
    override fun providerVMClass(): Class<MainViewModel>? =
        MainViewModel::class.java

    private lateinit var binding: ActivityMainBinding
    private var adapter = MainAdapter()

    override fun initView() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    override fun initData() {
        binding.recyclerView.adapter = adapter
        getData()
    }

    override fun setListener() {
        //房间列表的点击事件
        adapter.setOnItemClickListener { _, _, position ->
            viewModel.uiData.value?.run {
                val clickData = data[position]
                val json = GsonBuilder().create().toJson(clickData)
                NavigationUtils.goVideoListActivity(json)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            uiData.observe(this@MainActivity, Observer {
                adapter.setNewData(it.data)
            })
            errMsg.observe(this@MainActivity, Observer {
                Timber.e("加载失败$it")
            })
        }
    }

    override fun getData() {
        super.getData()
        viewModel.initData()
    }
}
