package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityStockBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class Stock : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    private var medList = mutableListOf<String>()
    private var displayList = mutableListOf<String>()

    private lateinit var deleteMed: String

    lateinit var bindingClass: ActivityStockBinding

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        bindingClass = ActivityStockBinding.inflate(getLayoutInflater())
        setContentView(bindingClass.root)

        medList.add("Анальгин")
        medList.add("Аугментин")
        medList.add("Ибупрофен")

        bindingClass.buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            val actionBar = supportActionBar

            if (actionBar != null) {
                actionBar.title = "Склад"

                actionBar.setDisplayHomeAsUpEnabled(true)
            }
        }

        displayList.addAll(medList)
        recyclerView = findViewById(R.id.recycleView)
        recyclerAdapter = RecyclerAdapter(displayList)

        recyclerView.adapter = recyclerAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT))
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean
        {
            var startPosition = viewHolder.adapterPosition
            var endPosition = target.adapterPosition

            Collections.swap(displayList, startPosition, endPosition)
            recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
        {
            var position = viewHolder.adapterPosition

            when(direction)
            {
                ItemTouchHelper.LEFT->
                {
                    deleteMed = displayList.get(position)
                    displayList.removeAt(position)
                    recyclerAdapter.notifyItemRemoved(position)

                    Snackbar.make(recyclerView, "$deleteMed is deleted", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                        displayList.add(position, deleteMed)
                        recyclerAdapter.notifyItemInserted(position)
                    }).show()

                }

                ItemTouchHelper.RIGHT -> {
                    var editText = EditText(this@Stock)
                    editText.setText(displayList[position])

                    val builder = AlertDialog.Builder(this@Stock)
                    builder.setTitle("Update an Item")
                    builder.setCancelable(true)
                    builder.setView(editText)

                    builder.setNegativeButton("cancel", DialogInterface.OnClickListener{dialog, which->
                        displayList.clear()
                        displayList.addAll(medList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    })
                    builder.setPositiveButton("update", DialogInterface.OnClickListener{dialog, which->
                        displayList.set(position, editText.getText().toString())
                        recyclerView.adapter!!.notifyItemChanged(position)
                    })
                    builder.show()
                }
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)

        var item: MenuItem = menu!!.findItem(R.id.search)

        if(item !=null) {
            var searchView = item.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        var search = newText.toLowerCase(Locale.getDefault())

                        for (med in medList) {
                            if (med.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(med)
                            }
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    } else {
                        displayList.clear()
                        displayList.addAll(medList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return true

                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}