package com.elmanko.mnkworkouts

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context, val items : ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){ // this class connects the elements from the xml to be used here via a view
        val llHistoryMainItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPosition = view.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_history_row,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size // wl -c
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items[position]
        holder.tvPosition.text = (position+1).toString()
        holder.tvItem.text = date

        if (position % 2 == 0 ){ // just some style, one line white, one gray
            holder.llHistoryMainItem.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else {
            holder.llHistoryMainItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }


    }
}