package com.northernboy.renthouse.ui.orderHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.OrderHistoryDetailFragmentBinding
import com.northernboy.renthouse.view.OrderView


class OrderHistoryDetailFragment : BaseBindingFragment<OrderHistoryDetailFragmentBinding>(R.layout.order_history_detail_fragment) {

    private  val args: OrderHistoryDetailFragmentArgs by navArgs()

    override fun initViewModel() {

    }

    override fun subscribe() {
        val orderView = Gson().fromJson(args.orderView, OrderView::class.java)
        dataBinding.item = orderView
    }


}
