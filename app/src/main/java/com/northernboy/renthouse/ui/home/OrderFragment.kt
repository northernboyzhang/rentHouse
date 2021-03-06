package com.northernboy.renthouse.ui.home

import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.Utils.rentLog
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.OrderFragmentBinding
import com.northernboy.renthouse.main.UsrViewModel
import com.northernboy.renthouse.view.HouseView
import kotlinx.android.synthetic.main.item_info.view.*

class OrderFragment : BaseBindingFragment<OrderFragmentBinding>(R.layout.order_fragment), AdapterView.OnItemSelectedListener {

    private val args : OrderFragmentArgs by navArgs()
    private lateinit var orderViewModel: OrderViewModel

    override fun initViewModel() {
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

        val usrView = getUsrView()
        dataBinding.orderUsrPhone.text = getString(R.string.order_usr_phone, usrView?.phone)
        dataBinding.orderUsrName.text = getString(R.string.order_usr_name, usrView?.name)


        dataBinding.orderPlace.setOnClickListener {
            if(usrView?.usrId == null){
                Toast.makeText(requireContext(), "订购失败，请登陆", Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.CENTER,0,0)
                }.show()
            }else{
                usrView.usrId?.let { it1 ->
                    orderViewModel.placeOrder(it1)
                    Toast.makeText(requireContext(), "订购成功！", Toast.LENGTH_LONG).apply {
                        setGravity(Gravity.CENTER,0,0)
                    }.show()
                    findNavController().navigateUp()
                }
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

