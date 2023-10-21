package com.example.travel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.travel.traveldata.requestAPI
import org.w3c.dom.Text

class SelectedFragment(val attraction:Int): Fragment() {
    private val TAG = "SelectedFragment"
    lateinit var webView: WebView
    var listener: ((visible: Int) -> Unit)? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.selected_item, container, false)
        val horizonView = view.findViewById<LinearLayout>(R.id.image_view_set)
        val request = requestAPI.travelInfo
        val imageArray = request.getJSONArray("data").getJSONObject(attraction).getJSONArray("images")
        Log.d(TAG, request.getJSONArray("data").getJSONObject(attraction).getJSONArray("images").length().toString())
        for(i in 0 until  imageArray.length()){
            val image = ImageView(context)
            image.load(imageArray.getJSONObject(i).get("src"))
            horizonView.addView(image)
        }
        view.findViewById<TextView>(R.id.attraction_title).setText(request.getJSONArray("data").getJSONObject(attraction).get("name").toString())
        view.findViewById<TextView>(R.id.attraction_introduction).setText(request.getJSONArray("data").getJSONObject(attraction).get("introduction").toString())
        view.findViewById<TextView>(R.id.attraction_address).setText("Address \n"+ request.getJSONArray("data").getJSONObject(attraction).get("address").toString())
        view.findViewById<TextView>(R.id.attraction_site).setText(request.getJSONArray("data").getJSONObject(attraction).get("official_site").toString())
        view.findViewById<TextView>(R.id.attraction_modified).setText("Last Updated Time  \n"+ request.getJSONArray("data").getJSONObject(attraction).get("modified").toString())

        webView = view.findViewById(R.id.web_view)
        if(!view.findViewById<TextView>(R.id.attraction_site).text.equals("")) {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(
                request.getJSONArray("data").getJSONObject(attraction).get("official_site")
                    .toString()
            )
            view.findViewById<TextView>(R.id.attraction_site).setOnClickListener {
                webView.visibility = VISIBLE
                listener?.invoke(webView.visibility)
            }
        }
        return view
    }
}