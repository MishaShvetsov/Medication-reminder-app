package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentAddMedBinding
import com.example.myapplication.databinding.FragmentUpdateMedBinding
import data.Med


class UpdateMed(private val med: Med) : DialogFragment() {

    private var _binding: FragmentUpdateMedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentUpdateMedBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(MedViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.editTextFullName.setText((med.nameMed))
        binding.editTextMed.setText((med.amountMed))
        binding.editDataMed.setText((med.DataMed))
        binding.editTextPeriodicity.setText((med.periodMed))


        binding.buttonUpdate.setOnClickListener{
            val medName = binding.editTextFullName.text.toString().trim()
            val medAmount = binding.editTextMed.text.toString().trim()
            val medDate = binding.editDataMed.text.toString().trim()
            val periodMed = binding.editTextPeriodicity.text.toString().trim()
            val periodSpinner = binding.spinMed
            val spin = periodSpinner.selectedItem.toString()

            if(medName.isEmpty()){
                binding.editTextFullName.error = "This field is required"
                return@setOnClickListener
            }

            if(medAmount.isEmpty()){
                binding.editTextMed.error = "This field is required"
                return@setOnClickListener
            }
            if(medDate.isEmpty()){
                binding.editDataMed.error = "This field is required"
                return@setOnClickListener
            }
            if(periodSpinner.isEmpty()){
                binding.editTextPeriodicity.error = "This field is required"
                return@setOnClickListener
            }

            med.nameMed = medName
            med.amountMed = medAmount
            med.DataMed = medDate
            med.periodMed = periodMed
            med.spinMed = spin

            viewModel.updateMed(med)
            dismiss()
            Toast.makeText(context,"Med has been update", Toast.LENGTH_SHORT).show()
        }
    }
}