package com.creativeprojects.medicall.ui.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.R

class AddressHistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    var item: CardView = itemView.findViewById(R.id.item)
    var title : TextView = itemView.findViewById(R.id.title)
    var subtitle : TextView = itemView.findViewById(R.id.subtitle)
    var remove : CardView = itemView.findViewById(R.id.clear_icon)
}