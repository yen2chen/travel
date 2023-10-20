package com.example.travel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.travel.traveldata.requestAPI

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val language = arrayOf("zh-tw", "zh-cn", "en", "ja", "ko", "es", "id", "th", "vi")
    private val apiRoot = "https://www.travel.taipei/open-api/"
    private val apiEnd = "/Attractions/All"

    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, ItemFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        val button = findViewById<ImageButton>(R.id.trans_button)
        button.setOnClickListener(View.OnClickListener { view ->
//            requestAPI.run(apiRoot+language[1]+apiEnd)
            Log.d(TAG, "click trans button")
//            updateAPI(1)
//            updateAdapter()
            val popupMenu: PopupMenu = PopupMenu(this,button)
            popupMenu.menuInflater.inflate(R.menu.translation_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.header_tw -> updateAPI(0)
                    R.id.header_cn -> updateAPI(1)
                    R.id.header_en -> updateAPI(2)
                    R.id.header_jp -> updateAPI(3)
                    R.id.header_kr -> updateAPI(4)
                    R.id.header_es -> updateAPI(5)
                    R.id.header_id -> updateAPI(6)
                    R.id.header_th -> updateAPI(7)
                    R.id.header_vi -> updateAPI(8)
                }
                true
            })
            popupMenu.show()
        })
        updateAPI(0)

        checkTravelData()
    }


    fun updateAdapter(){
        println("updateAdapter")
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ItemFragment()).commitNow()
    }
    fun updateAPI(selectedLanguage: Int){
        requestAPI.run(apiRoot+language[selectedLanguage]+apiEnd)
    }

    fun checkTravelData(){
        if(requestAPI.update){
            updateAdapter()
            requestAPI.update = false
        }
        Handler().postDelayed(Runnable { checkTravelData() }, 1000)
    }
}