package com.creativeprojects.medicall.ui.holder

import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.databinding.ListitemDoctorInboxBinding

class DoctorInboxViewHolder(binding: ListitemDoctorInboxBinding): RecyclerView.ViewHolder(binding.root) {
    val itemView = binding.parentCard
    val textDate = binding.txtDate
    val textHour = binding.txtHour
    val textStatus = binding.txtStatus
    val textCause = binding.txtCause
    val textAddress = binding.txtAddress
    val buttonCancel = binding.btnCancel
    val buttonProceed = binding.btnProceed
    var cardStatus = binding.statusIcon
}