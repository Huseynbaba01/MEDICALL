package com.creativeprojects.medicall.ui.fragment.general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.creativeprojects.medicall.R
import com.creativeprojects.medicall.databinding.FragmentConnectionErrorBinding
import com.creativeprojects.medicall.utils.helper.CommonHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConnectionError : Fragment() {
    lateinit var binding: FragmentConnectionErrorBinding
    val TAG = "MyTagHere"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnectionErrorBinding.inflate(inflater)
        binding.mainFrame.setOnClickListener {
            val fireStore = Firebase.firestore
            fireStore.collection("accepted")
                .whereEqualTo("accepted",true)
                .get().addOnSuccessListener {
                    Log.d(TAG, "checkConnection: ${CommonHelper.isNetworkAvailable(requireContext())},${it.isEmpty}")
                    if(CommonHelper.isNetworkAvailable(requireContext()) && !it.isEmpty){
                        findNavController().navigate(ConnectionErrorDirections.actionConnectionErrorToIntroFragment())
                    }
                }
        }
        return binding.root
    }

}