package com.example.myapplication

import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentFirebaseBinding


class FirebaseFragment : Fragment() {


    private var _binding: FragmentFirebaseBinding? = null
    private val binding get() = _binding!!
    private val adapter = MedAdapter()
    private lateinit var viewModel: MedViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirebaseBinding.inflate(inflater, container,false)
        viewModel = ViewModelProvider(this).get(MedViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMed.adapter = adapter

        binding.addButton.setOnClickListener{
        AddMed().show(childFragmentManager, "")
        }

        viewModel.med.observe(viewLifecycleOwner, Observer {
            adapter.addMed(it)
        })
        viewModel.getRealTimeUpdate()

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMed)
    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
           return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            var currentMed = adapter.meds[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateMed(currentMed).show(childFragmentManager, "")
                }
                ItemTouchHelper.LEFT -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure you want to delete this med?")
                        it.setPositiveButton("Yes"){dialog,which ->
                            viewModel.deleteMed(currentMed)
                            binding.recyclerViewMed.adapter?.notifyItemRemoved(position)
                            Toast.makeText(context, "Med has been deleted", Toast.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }
            binding.recyclerViewMed.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroy(){

        super.onDestroy()
        _binding = null
    }

}

