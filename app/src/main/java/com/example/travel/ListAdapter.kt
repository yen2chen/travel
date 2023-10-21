package com.example.travel

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ListAdapter(val context: Context, val nameArray: ArrayList<ArrayList<String>>): RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    var onItemClick: ((Int) -> Unit)?  = null
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText = itemView?.findViewById<TextView>(R.id.travel_title)
        val descriptionText = itemView?.findViewById<TextView>(R.id.travel_description)
        val fig = itemView?.findViewById<ImageView>(R.id.travel_fig)

        fun bind(data: ArrayList<String>){
            nameText?.text = data[0]
            descriptionText?.text = data[1]
            fig?.load(data[2])
            if(data[2].equals("Not provide")){
                fig?.setBackgroundResource(R.drawable.ic_launcher_foreground)
            }
        }

        init {
            itemView.setOnClickListener{
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.my_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nameArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(nameArray[position])
    }
}