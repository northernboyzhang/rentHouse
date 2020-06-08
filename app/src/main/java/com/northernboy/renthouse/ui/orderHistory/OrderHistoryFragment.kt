package com.northernboy.renthouse.ui.orderHistory

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.OrderHistoryBinding
import com.northernboy.renthouse.databinding.OrderHistoryFragmentBinding
import com.northernboy.renthouse.view.OrderView

class OrderHistoryFragment : BaseBindingFragment<OrderHistoryFragmentBinding>(R.layout.order_history_fragment) {

    private lateinit var orderHistoryViewModel: OrderHistoryViewModel

    override fun initViewModel() {
        orderHistoryViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
    }

    override fun subscribe() {
        dataBinding.recyclerOrderHistoryView.apply {
            adapter = OrderViewAdapter()
            layoutManager = LinearLayoutManager(requireContext())
        }

        orderHistoryViewModel.orderViewList.observe(viewLifecycleOwner, Observer {
            val adapter = dataBinding.recyclerOrderHistoryView.adapter as OrderViewAdapter
            adapter.submitList(it)
        })
    }


}
class OrderViewAdapter: NViewAdapter<OrderView>(DiffCallBack()){
    override fun touchEvent(binding: ViewDataBinding) {
        val dataBinding = binding as OrderHistoryBinding
        dataBinding.root.setOnClickListener {
            val action = OrderHistoryFragmentDirections.actionNavigationOrderHistoryToOrderHistoryDetail(binding.item.toString())
            it.findNavController().navigate(action)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.order_history
    }
}
