package com.northernboy.renthouse.ui.personal

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.northernboy.renthouse.R
import com.northernboy.renthouse.Utils.centerToast
import com.northernboy.renthouse.Utils.getUsrView
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.databinding.FragmentPersonalBinding
import com.northernboy.renthouse.main.UsrViewModel

class PersonalFragment : BaseBindingFragment<FragmentPersonalBinding>(R.layout.fragment_personal) {

    private lateinit var personalViewModel: PersonalViewModel
    private lateinit var usrViewModel: UsrViewModel

    override fun initViewModel() {
        personalViewModel = ViewModelProvider(this).get(PersonalViewModel::class.java)
        usrViewModel = ViewModelProvider(requireActivity()).get(UsrViewModel::class.java)
    }

    override fun subscribe() {
        setFunc()
        usrViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            dataBinding.login.visibility = if (it) View.INVISIBLE else View.VISIBLE
        })
        dataBinding.apply {
            login.setOnClickListener {
                findNavController().navigate(R.id.navigation_login)
            }
            logout.setOnClickListener {
                usrViewModel.logout()
            }
            registerInformation.setOnClickListener {
                findNavController().navigate(R.id.navigation_register)
            }
            manageOwnHouse.setOnClickListener {
                if(getUsrView()?.usrId != null){
                    findNavController().navigate(R.id.navigation_manage_own_house)
                }else{
                    centerToast(requireContext(), getString(R.string.warn_login_first))
                }
            }
            manageOrder.setOnClickListener {
                findNavController().navigate(R.id.navigation_order_history)
            }
            myReserve.setOnClickListener {
                findNavController().navigate(R.id.navigation_reserve)
            }
        }
        dataBinding.item = getUsrView()

    }

    private fun setFunc() {

    }
}
