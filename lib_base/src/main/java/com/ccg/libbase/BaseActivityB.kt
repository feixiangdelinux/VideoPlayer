package com.ccg.libbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:35
 */
abstract class BaseActivityB <VM : BaseViewModelB> : AppCompatActivity() {
    lateinit var viewModel: VM
    private val compositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        initView()
        initData()
        setListener()
        startObserve()
    }
    /**
     * 获取数据
     */
    open fun getData() {

    }

    /**
     * 初始化视图控件
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 设置监听
     */
    abstract fun setListener()

    /**
     * 开始数据监听
     */
    open fun startObserve() {}

    private fun initVM() {
        providerVMClass()?.let {
            viewModel = ViewModelProvider(this).get(it)
            viewModel.let(lifecycle::addObserver)
        }
    }

    open fun providerVMClass(): Class<VM>? = null
    /**
     * 添加订阅
     * @param disposable Disposable
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    override fun onDestroy() {
        viewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
        compositeDisposable.clear()
    }
}