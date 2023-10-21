package com.example.travel.traveldata

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import org.json.JSONObject

class requestAPI {
    companion object {
        private val TAG = "requestAPI"
        var travelInfo = JSONObject()
        var update = false
        fun addJson(jObj: JSONObject){
            travelInfo = jObj
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
                    val jsonTotal = JSONObject(response.body?.string())
                    addJson(jsonTotal)
                    Log.d(TAG,  jsonTotal.toString())
                    update = true
                }
            })
        }
    }
}