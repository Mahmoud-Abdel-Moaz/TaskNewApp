package com.mahmoud.tasknewapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mahmoud.tasknewapp.R

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var iv_news_img: ImageView
    lateinit var news_title: TextView
    lateinit var news_description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        var intent=getIntent()

        iv_news_img=findViewById(R.id.iv_news_img)
        news_title=findViewById(R.id.news_title)
        news_description=findViewById(R.id.news_description)

        var title=intent.getStringExtra("title")
        var description=intent.getStringExtra("description")
        var imgUrl=intent.getStringExtra("imgUrl")

        news_title.text=title
        news_description.text=description

        Glide.with(this).load(imgUrl).into(iv_news_img)
    }
}