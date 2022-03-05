package com.creativeprojects.medicall.ui.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.R

class AddressHistoryViewHolder(var itemView : View) : RecyclerView.ViewHolder(itemView){
    var title : TextView = itemView.findViewById(R.id.title)
    var subtitle : TextView = itemView.findViewById(R.id.subtitle)
    var remove : ImageView = itemView.findViewById(R.id.clear_icon)
}