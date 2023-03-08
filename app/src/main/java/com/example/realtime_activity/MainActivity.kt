package com.example.realtime_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var count = 1
    val db = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myRef = db.reference
        getValueFromRealtime()
        AddToRealtime.setOnClickListener {
            val data = hashMapOf(
                "name" to personName.text.toString(),
                "ID" to personId.text.toString(),
                "Age" to personaAge.text.toString()
            )
            myRef.child("Person").child("$count Row").setValue(data)
            Toast.makeText(this, "Success to Add", Toast.LENGTH_LONG).show()
            personName.text.clear()
            personaAge.text.clear()
            personId.text.clear()

        }
        Show.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    textViewShowData.clearComposingText()
                    textViewShowData.text = value.toString()
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    Log.d("aa" , value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
                }

            })
        }

    }
    // To see how many rows there are in the person collection
    fun getValueFromRealtime() {
        val myRef = db.getReference("Person")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var value = snapshot.childrenCount
                count = value.toInt() + 1
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}