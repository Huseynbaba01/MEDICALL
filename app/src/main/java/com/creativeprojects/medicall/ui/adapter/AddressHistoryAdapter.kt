package com.creativeprojects.medicall.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.model.AddressHistoryItem
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.event.HistoryItemDeletedEvent
import com.creativeprojects.medicall.event.HistoryItemSelectedEvent
import com.creativeprojects.medicall.ui.holder.AddressHistoryViewHolder
import org.greenrobot.eventbus.EventBus

class AddressHistoryAdapter(var items : List<AddressHistoryItem>) : RecyclerView.Adapter<AddressHistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHistoryViewHolder {
        return AddressHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_address_history, parent, false))
    }

    override fun onBindViewHolder(holder: AddressHistoryViewHolder, position: Int) {
//        holder.title.text = set location title
//        holder.subtitle.text = set location subtitle
        holder.title.text = items[position].title
        holder.subtitle.text = items[position].subtitle

        holder.item.setOnClickListener {
            Log.d("MyTagHere", "onBindViewHolder: 27")
            EventBus.getDefault().post(HistoryItemSelectedEvent(items[position]))
        }
        holder.remove.setOnClickListener {
            EventBus.getDefault().postSticky(HistoryItemDeletedEvent(items[position]))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateDataSet(newItems : List<AddressHistoryItem>){
        this.items = newItems
        notifyDataSetChanged()
    }


}