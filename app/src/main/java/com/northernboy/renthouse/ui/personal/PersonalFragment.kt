package com.northernboy.renthouse.ui.personal

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.northernboy.renthouse.R
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
        dataBinding.login.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
        }
        dataBinding.logout.root.setOnClickListener {
            usrViewModel.logout()
        }
    }

    private fun setFunc() {
        dataBinding.apply {
            confirmId.apply {
                funcName = getString(R.string.confirm_id)
            }
            manageOwnHouse.funcName = getString(R.string.manage_own_house)
            manageRentHouse.funcName = getString(R.string.manage_rent_house)
            managePost.funcName = getString(R.string.manage_post)
            logout.apply {
                funcName = getString(R.string.logout)
            }
        }
    }
}
