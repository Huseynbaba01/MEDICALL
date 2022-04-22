package com.creativeprojects.medicall.ui.fragment.doctor_pages

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creativeprojects.medicall.databinding.DoctorHomeFragmentBinding
import com.creativeprojects.medicall.model.DoctorInboxItem
import com.creativeprojects.medicall.ui.adapter.DoctorInboxAdapter
import com.creativeprojects.medicall.utils.helper.model.DiseaseType
import com.creativeprojects.medicall.utils.helper.model.DoctorInboxStatus
import com.creativeprojects.medicall.viewmodel.DoctorHomeViewModel
import java.util.*
import kotlin.collections.ArrayList

class DoctorHomeFragment : Fragment() {


    private lateinit var viewModel: DoctorHomeViewModel
    private lateinit var binding: DoctorHomeFragmentBinding
    private lateinit var inboxItems: MutableList<DoctorInboxItem>
    private lateinit var adapter: DoctorInboxAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DoctorHomeFragmentBinding.inflate(inflater, container, false)
        inboxItems = ArrayList()
        adapter = DoctorInboxAdapter(emptyList())
        binding.inboxItems.adapter = adapter
        binding.inboxItems.layoutManager = LinearLayoutManager(requireContext())
        inboxItems.add(
            DoctorInboxItem(
                Calendar.getInstance().timeInMillis - 4444,
                DoctorInboxStatus.DONE,
                "Neftçilər pr 125",
                DiseaseType.FEVER, null
            )
        )
        inboxItems.add(
            DoctorInboxItem(
                Calendar.getInstance().timeInMillis - 33333,
                DoctorInboxStatus.ACCEPTED,
                "Bakı Mühəndislik Universiteti",
                DiseaseType.HEART_STROKE, null
            )
        )
        inboxItems.add(
            DoctorInboxItem(
                Calendar.getInstance().timeInMillis - 222222,
                DoctorInboxStatus.NOT_ACCEPTED,
                "Şəmkir, Könüllü k",
                DiseaseType.BLEEDING, null
            )
        )
        inboxItems.add(
            DoctorInboxItem(
                Calendar.getInstance().timeInMillis - 1111111,
                DoctorInboxStatus.NOT_ACCEPTED,
                "Lənkəran, Separati k.",
                DiseaseType.CORONA, null
            )
        )
        adapter.updateDataSet(inboxItems)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DoctorHomeViewModel::class.java]
        // TODO: Use the ViewModel
    }

}