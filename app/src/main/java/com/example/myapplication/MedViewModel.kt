package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import data.Med
import data.NODE_MED

class MedViewModel:ViewModel() {
    private val dbmeds = FirebaseDatabase.getInstance().getReference(NODE_MED)

    private val _result = MutableLiveData<Exception?>()
    val result:LiveData<Exception?> get() = _result

    private val _med = MutableLiveData<Med>()
    val med: LiveData<Med> get() = _med

    fun addMed(med: Med){
        med.id = dbmeds.push().key

        dbmeds.child(med.id!!).setValue(med).addOnCompleteListener{
            if(it.isSuccessful){
                _result.value = null
            }
            else{
                _result.value = it.exception
            }

        }
    }

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val med = snapshot.getValue(Med::class.java)
            med?.id = snapshot.key
            _med.value = med!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val med = snapshot.getValue(Med::class.java)
            med?.id = snapshot.key
            _med.value = med!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val med = snapshot.getValue((Med::class.java))
            med?.id = snapshot.key
            med?.isDeleted = true
            _med.value = med!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    fun getRealTimeUpdate(){
        dbmeds.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbmeds.removeEventListener(childEventListener)
    }

    fun updateMed(med: Med){
        dbmeds.child(med.id!!).setValue(med)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _result.value = null
                }
                else{
                    _result.value = it.exception
                }
            }
    }

    fun deleteMed(med: Med){
        dbmeds.child(med.id!!).setValue(null)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    _result.value = null
                }
                else{
                    _result.value = it.exception
                }
            }
    }
}