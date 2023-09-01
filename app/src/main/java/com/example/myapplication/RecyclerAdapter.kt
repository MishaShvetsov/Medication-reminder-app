package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(medList: MutableList<String>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var med :MutableList<String> = medList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_lyout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return med.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemTitle.text = med[position]
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
     var itemImage: ImageView
     var itemTitle: TextView

    init{
        itemImage = itemView.findViewById(R.id.itemImage)
        itemTitle = itemView.findViewById(R.id.itemTitle)

        itemView.setOnClickListener{
            val position: Int = adapterPosition
            Toast.makeText(itemView.context, "You click on ${med[position]}", Toast.LENGTH_LONG).show()
        }
    }
    }
}