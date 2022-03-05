package com.creativeprojects.medicall.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.AddressHistoryItem
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.ui.holder.AddressHistoryViewHolder

class AddressHistoryAdapter(var items : List<AddressHistoryItem>) : RecyclerView.Adapter<AddressHistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHistoryViewHolder {
        return AddressHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_address_history, parent, false))
    }

    override fun onBindViewHolder(holder: AddressHistoryViewHolder, position: Int) {
//        holder.title.text = set location title
//        holder.subtitle.text = set location subtitle
        holder.title.text = "Həzi Aslanov"
        holder.subtitle.text = "Bakı, Azərbaycan"

        holder.remove.setOnClickListener {
            //TODO Clear the place from the list
            Toast.makeText(holder.remove.context, "The item in the line $position should be deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
//        return items.size
        return 3 //TODO Remove, it is just for test
    }


}