package com.example.travel.traveldata

import com.example.travel.ItemFragment
import com.example.travel.MainActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import org.json.JSONObject

class requestAPI {
    companion object {
        var travelInfo = JSONObject()
        fun addJson(jarray: JSONObject){
            travelInfo = jarray
        }

        fun run(url: String) {
            val request = Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
//                    println(response.body()?.string())
                    val jsonTotal = JSONObject(response.body()?.string())
                    addJson(jsonTotal)
                    println( jsonTotal.toString())
                    ItemFragment().updateAdapter()
                }
            })
        }
    }
}