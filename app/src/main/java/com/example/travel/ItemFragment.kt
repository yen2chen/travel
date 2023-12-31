package com.example.travel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travel.traveldata.requestAPI
import org.json.JSONException

class ItemFragment : Fragment() {
    private val TAG = "ItemFragment"
    private lateinit var view:View
    private lateinit var testAdapter:ListAdapter
    var listener: ((selectedPosition: Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_item_list, container, false)

        var requestDone = false
        var retry = 5
        while(!requestDone || retry == 0) {
            requestDone = requestAPI.travelInfo.has("data")
            retry--
            Thread.sleep(1000)
        }
        val request = requestAPI.travelInfo
//        println(request.getJSONArray("data").getJSONObject(0).get("name").toString())
        Log.d(TAG, request.getJSONArray("data").getJSONObject(0).getJSONArray("images").toString())
        val dataArray = ArrayList<ArrayList<String>>()
        for (i in 0 until request.getJSONArray("data").length()) {
            val tourArray = ArrayList<String>()
            try {
                tourArray.add(request.getJSONArray("data").getJSONObject(i).get("name").toString())
                tourArray.add(request.getJSONArray("data").getJSONObject(i).get("introduction").toString())
                tourArray.add(request.getJSONArray("data").getJSONObject(i).getJSONArray("images").getJSONObject(0).get("src").toString())
            }catch (e: JSONException){
                Log.e(TAG, e.printStackTrace().toString())
                while(tourArray.size < 3){
                    tourArray.add("Not provide")
                }
            }
            dataArray.add(tourArray)
        }

        val layoutManager = LinearLayoutManager(this.context)

        testAdapter = ListAdapter(requireContext(), dataArray)
        testAdapter.onItemClick = { contacts ->
            Log.d(TAG, contacts.toString())
            listener?.invoke(contacts)
        }
        (view as RecyclerView).layoutManager = layoutManager
        (view as RecyclerView).adapter = testAdapter
        Log.d(TAG, "Get data $dataArray")

        return view
    }
}