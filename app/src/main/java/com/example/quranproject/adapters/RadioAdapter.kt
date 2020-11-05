package com.example.quranproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranproject.R
import com.example.quranproject.api.model.RadiosItem
import com.example.quranproject.api.model.RadiosResponse
import kotlinx.android.synthetic.main.radio_item.view.*

class RadioAdapter(list: ArrayList<RadiosItem?>?) :RecyclerView.Adapter<RadioAdapter.ViewHolder>() {
    var list = ArrayList<RadiosItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.radio_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.name.text = list.get(position)?.name
        holder.itemView.setOnClickListener {
            channelClickListener?.onItemClicked(position,item!!)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val name = itemView.name


    }

    init {
        this.list = list!!
    }

    var channelClickListener: onRadioClickListener? = null

    interface onRadioClickListener{
        fun onItemClicked(pos:Int,item :RadiosItem)
    }
    fun onChannelClickListener(listener:onRadioClickListener){
        this.channelClickListener = listener
    }

    fun changeData(list: ArrayList<RadiosItem?>?){
        if (list != null) {
            this.list = list
        }
        notifyDataSetChanged()
    }


}