package com.northernboy.renthouse.ui.bbs

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson

import com.northernboy.renthouse.R
import com.northernboy.renthouse.base.BaseBindingFragment
import com.northernboy.renthouse.base.NViewAdapter
import com.northernboy.renthouse.databinding.FragmentReplyBinding
import com.northernboy.renthouse.view.PostView
import com.northernboy.renthouse.view.ReplyView

/**
 * A simple [Fragment] subclass.
 */
class ReplyFragment : BaseBindingFragment<FragmentReplyBinding>(R.layout.fragment_reply) {

    private val args : ReplyFragmentArgs by navArgs()
    lateinit var replyViewModel : ReplyViewModel

    override fun initViewModel() {
        replyViewModel = ViewModelProvider(this).get(ReplyViewModel::class.java)
        replyViewModel.postView.value = Gson().fromJson(args.postView,PostView::class.java)
    }

    override fun subscribe() {
        dataBinding.recyclerReplyView.apply {
            adapter = ReplyViewAdapter()
            layoutManager = LinearLayoutManager(context)
        }
        val adapter = dataBinding.recyclerReplyView.adapter as ReplyViewAdapter

        replyViewModel.postView.observe(viewLifecycleOwner, Observer {
            dataBinding.item = it
        })

        replyViewModel.replyViewList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
class ReplyViewAdapter: NViewAdapter<ReplyView>(DiffCallBack()){
    override fun touchEvent(binding: ViewDataBinding) {
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.reply_view
    }
}