package com.northernboy.renthouse.ui.home

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.rentLog
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.OrderFragmentBinding
import com.northernboy.renthouse.main.UsrViewModel
import com.northernboy.renthouse.view.HouseView
import kotlinx.android.synthetic.main.item_info.view.*

class OrderFragment : BaseBindingFragment<OrderFragmentBinding>(R.layout.order_fragment), AdapterView.OnItemSelectedListener {

    private val args : OrderFragmentArgs by navArgs()
    private lateinit var usrViewModel: UsrViewModel
    private lateinit var orderViewModel: OrderViewModel

    override fun initViewModel() {
        usrViewModel = ViewModelProvider(requireActivity()).get(UsrViewModel::class.java)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        orderViewModel.houseView.value = Gson().fromJson(args.houseView, HouseView::class.java)
    }

    override fun subscribe() {
        orderViewModel.houseView.observe(viewLifecycleOwner, Observer {
            rentLog(it.toString()+ "Order")
            dataBinding.apply {
                orderHouseAddress.orderInfo.text =
                    getString(R.string.order_house_address, it.houseAddress)
                orderHouseMaxCap.orderInfo.text =
                    getString(R.string.order_house_capacity, it.houseCapacity.toString())
                orderHouseMonthRent.orderInfo.text =
                    getString(R.string.order_house_rent, it.houseRent.toString())
                orderHouseType.orderInfo.text =
                    getString(R.string.order_house_type, it.houseType)
            }
        })

        usrViewModel.usrView.observe(viewLifecycleOwner, Observer {
            rentLog(it.toString() + "Order")
            dataBinding.orderUsrPhone.text = getString(R.string.order_usr_phone, it.phone)
            dataBinding.orderUsrName.text = getString(R.string.order_usr_name, it.name)
        })

        dataBinding.orderPlace.setOnClickListener {
            usrViewModel.usrView.value?.usrId?.let { it1 ->
                orderViewModel.placeOrder(it1)
                findNavController().navigateUp()
            }
        }
        setupSpinner()
    }

    private fun setupSpinner(){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.month_number,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dataBinding.monthSpinner.adapter = it
        }
        dataBinding.monthSpinner.onItemSelectedListener = this

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val a = parent?.getItemAtPosition(position) as String
        orderViewModel.month = a.toInt()
        dataBinding.orderTotalRent.text = getString(R.string.order_total_rent, orderViewModel.calculate().toString())
    }
}

