package com.mahmoud.tasknewapp.pojo


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("author")
    val author: Any,
    @SerializedName("content")
    val content: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)