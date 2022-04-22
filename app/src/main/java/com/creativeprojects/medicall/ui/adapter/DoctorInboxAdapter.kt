package com.creativeprojects.medicall.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.ListitemDoctorInboxBinding
import com.creativeprojects.medicall.model.DoctorInboxItem
import com.creativeprojects.medicall.ui.holder.DoctorInboxViewHolder
import com.creativeprojects.medicall.utils.helper.TimeHelper
import com.creativeprojects.medicall.utils.helper.model.DiseaseType
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus.NOT_ACCEPTED
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus.ACCEPTED
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus.DONE

class DoctorInboxAdapter(var items: List<DoctorInboxItem>) :
    RecyclerView.Adapter<DoctorInboxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorInboxViewHolder {
        return DoctorInboxViewHolder(
            ListitemDoctorInboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorInboxViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.textDate.text = TimeHelper.dateToFormattedString(
            TimeHelper.timeInMillisToDate(item.date),
            "dd.MM.yyyy"
        )
        holder.textHour.text = TimeHelper.dateToFormattedString(
            TimeHelper.timeInMillisToDate(item.date),
            "hh:mm"
        )
        holder.textAddress.text = item.address

        if(item.cause == DiseaseType.OTHER)
            holder.textCause.text = item.message
        else holder.textCause.text = item.cause.toString()
        when (item.status) {
            NOT_ACCEPTED -> {
                holder.textStatus.setText(R.string.title_waiting)
                holder.cardStatus.setCardBackgroundColor(context.getColor(R.color.status_yellow))
                holder.buttonProceed.setBackgroundColor(context.getColor(R.color.main_blue))
                holder.buttonProceed.setText(R.string.title_accept)
            }
            ACCEPTED  -> {
                holder.textStatus.setText(R.string.title_been_accepted)
                holder.cardStatus.setCardBackgroundColor(context.getColor(R.color.status_green))
                holder.buttonProceed.setBackgroundColor(context.getColor(R.color.green))
                holder.buttonProceed.setText(R.string.title_accepted)
            }
            DONE -> {
                holder.textStatus.setText(R.string.title_been_accepted)
                holder.cardStatus.setCardBackgroundColor(holder.cardStatus.context.getColor(R.color.status_green))
                holder.buttonProceed.setBackgroundColor(holder.cardStatus.context.getColor(R.color.main_blue))
                holder.buttonProceed.setText(R.string.title_end_request)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateDataSet(newItems: List<DoctorInboxItem>){
        items = newItems
        notifyDataSetChanged()
    }


}