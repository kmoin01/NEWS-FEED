package com.moin.breakingnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
class MainActivity : AppCompatActivity() , NewsClicked{
    private lateinit var  mAdapter : NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       recyclerView.layoutManager= LinearLayoutManager(this)
       getData()
        mAdapter = NewsAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun getData(){
        val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=997232f60f24472490f9ae34e0242f37"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null ,
            {val JsonArraynews = it.getJSONArray("articles")
                val Arraynews = ArrayList<NewsData>()
                for(i in 0 until JsonArraynews.length()) {
                    val JsonObjectnews = JsonArraynews.getJSONObject(i)
                    val newsdata = NewsData(
                            JsonObjectnews.getString("title"),
                            JsonObjectnews.getString("author"),
                            JsonObjectnews.getString("url"),
                            JsonObjectnews.getString("urlToImage")
                    )
                    Arraynews.add(newsdata)
                }

                mAdapter.newsUpdate(Arraynews)
            },
            {

             }


        )
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(items: NewsData) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(items.url))
    }
}