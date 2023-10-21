package com.example.travel

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.example.travel.traveldata.requestAPI

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val language = arrayOf("zh-tw", "zh-cn", "en", "ja", "ko", "es", "id", "th", "vi")
    private val apiRoot = "https://www.travel.taipei/open-api/"
    private val apiEnd = "/Attractions/All"
    private var lastLanguage = 0
    private var lastAttraction = -1
    private lateinit var button: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initFragment()

        button = findViewById<ImageButton>(R.id.trans_button)
        button.setOnClickListener(View.OnClickListener { view ->
            Log.d(TAG, "click trans button")
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
        updateAPI(lastLanguage)

        checkTravelData()
    }

    fun updateAdapter(){
        println("updateAdapter")
        val fragment = ItemFragment()
        fragment.listener = { position -> run { goAttraction(position) } }
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commitNow()
    }
    fun updateAPI(selectedLanguage: Int){
        requestAPI.run(apiRoot+language[selectedLanguage]+apiEnd)
        lastLanguage = selectedLanguage
    }

    fun checkTravelData(){
        if(requestAPI.update){
            println("YCC " + supportFragmentManager.fragments.first().toString())
            when(supportFragmentManager.fragments.first()) {
                is ItemFragment -> updateAdapter()
                is SelectedFragment -> goAttraction(lastAttraction)
            }

            requestAPI.update = false
        }
        Handler().postDelayed(Runnable { checkTravelData() }, 1000)
    }

    fun goAttraction(number: Int){
        println("YCC2 " + number)
        lastAttraction = number
        val selectedFragment = SelectedFragment(number)
        selectedFragment.listener = {_ ->
            println("YCC click link")
            button.visibility = View.GONE
        }
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, selectedFragment).commitNow()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        println("YCC back button")
        if(supportFragmentManager.fragments.first() is SelectedFragment && (supportFragmentManager.fragments.first() as SelectedFragment).webView.visibility == View.VISIBLE){
            println("YCC is visible")
            (supportFragmentManager.fragments.first() as SelectedFragment).webView.visibility = View.GONE
            button.visibility = View.VISIBLE
        }else {
            updateAdapter()
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
        return true
    }

    fun initFragment(){
        val fragment = ItemFragment()
        fragment.listener = { position -> run { goAttraction(position) } }
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).addToBackStack(null).commit()
    }
}