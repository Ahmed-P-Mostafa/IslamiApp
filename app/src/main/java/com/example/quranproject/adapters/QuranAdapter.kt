package com.example.quranproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quranproject.R

class QuranAdapter(var context: Context, list: Array<String?>) :

    RecyclerView.Adapter<QuranAdapter.viewHolder>() {
    var list: Array<String?>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.soura_item, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textView.text = list[position]
        holder.imageView.setOnClickListener(View.OnClickListener { if (listener!= null){

            listener?.onItemClicked(position)
        }})
    }
    override fun getItemCount(): Int {
        return list.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imageView:ImageView

        init {
           textView = itemView.findViewById(R.id.item_TV)
            imageView = itemView.findViewById(R.id.item_IV)
        }
    }
    init {
        this.list = list
    }

    // interface callBack to be triggered when any sura clicked
    // the implementation  @QuranFragment

    public interface OnItemClickListenerInterface{
        fun onItemClicked(pos: Int)
    }
     var listener: OnItemClickListenerInterface? = null

    fun onSuraClicked(listener: OnItemClickListenerInterface){
        this.listener = listener
    }

    companion object {
        lateinit var OnItemClickListenerInterface: Any
    }
}

