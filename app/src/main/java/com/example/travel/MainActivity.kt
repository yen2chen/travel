package com.example.travel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.travel.traveldata.requestAPI
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var initArray = arrayOf("None0", "None1")
    val apiRoot = "https://www.travel.taipei/open-api/zh-tw/Attractions/All"
    var arrayAdapter: ArrayAdapter<String>? = null
    var listView: ListView? = null
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, ItemFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,initArray)

        listView = findViewById<ListView>(R.id.listView)
        listView?.adapter = arrayAdapter
        listView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val selectedItem = adapterView.getItemAtPosition(position) as String
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            Toast.makeText(
                applicationContext,
                "click item $selectedItem its position $itemIdAtPos",
                Toast.LENGTH_SHORT
            ).show()
        }

        val button = findViewById<Button>(R.id.trans_button)
        button.setOnClickListener(View.OnClickListener { view ->
            requestAPI.run(apiRoot)
            Log.d(TAG, "click trans button")
        })
    }
}