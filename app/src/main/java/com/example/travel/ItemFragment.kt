package com.example.travel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.travel.placeholder.PlaceholderContent
import com.example.travel.traveldata.requestAPI

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 1
     private lateinit var view:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_item_list, container, false)

//        requestAPI.run("https://www.travel.taipei/open-api/zh-tw/Attractions/All")
        Log.d("Fragment", "onCreateView")
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                (view as RecyclerView).layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                (view as RecyclerView).adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    fun updateAdapter(){
        val array = arrayListOf<String>()
        val request = requestAPI.travelInfo
        for(i in 0..request.length()){
            array.add(request.getJSONArray("data").getJSONObject(i).get("name").toString())
        }

        var adapter = (view as RecyclerView).adapter
        val arrayAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1,array)
//        adapter = arrayAdapter
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}