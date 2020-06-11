package com.northernboy.norotextview.custom.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseBindingFragment<T: ViewDataBinding>(private val layoutId: Int): Fragment(){

    lateinit var dataBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        initViewModel()
        subscribe()
        return dataBinding.root
    }
    abstract fun initViewModel()
    abstract fun subscribe()
}