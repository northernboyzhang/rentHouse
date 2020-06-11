package com.northernboy.norotextview.custom.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.northernboy.norotextview.BR


//T: The list data type
abstract class NViewAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, NViewHolder<T>>(diffCallback){

    class DiffCallBack<T>: DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
            .inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return NViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
        touchEvent(holder.binding)
    }

    abstract fun touchEvent(binding: ViewDataBinding)
}

class NViewHolder<T>(val binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(item: T){
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}


