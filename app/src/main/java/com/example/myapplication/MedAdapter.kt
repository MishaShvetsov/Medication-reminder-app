package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerViewMedBinding
import data.Med

class MedAdapter: RecyclerView.Adapter<MedAdapter.ViewHolder>() {

    var meds = mutableListOf<Med>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewMedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewName.text = meds[position].nameMed
        holder.binding.textViewMed.text = meds[position].amountMed

        holder.binding.textViewMedData.text = meds[position].DataMed
        holder.binding.textViewMedPeriod.text = meds[position].periodMed

        holder.binding.textViewMedSpin.text = meds[position].spinMed
    }

    override fun getItemCount(): Int {
        return meds.size
    }

    fun addMed(med: Med){
        if(!meds.contains(med)){
            meds.add(med)
        }
        else{
            val index = meds.indexOf(med)
            if(med.isDeleted){
                meds.removeAt(index)
            }
            else{
                meds[index] = med
            }

        }
        notifyDataSetChanged()
    }



    inner class ViewHolder(val binding: RecyclerViewMedBinding): RecyclerView.ViewHolder(binding.root)
}