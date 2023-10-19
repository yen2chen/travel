package com.example.travel

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    val language = arrayOf("0","1")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,language)

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = arrayAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            Toast.makeText(
                applicationContext,
                "click item $selectedItem its position $itemIdAtPos",
                Toast.LENGTH_SHORT
            ).show()
        }
        }
}