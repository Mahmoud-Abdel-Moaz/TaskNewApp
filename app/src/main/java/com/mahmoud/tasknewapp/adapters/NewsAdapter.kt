package com.mahmoud.tasknewapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoud.tasknewapp.R
import com.mahmoud.tasknewapp.pojo.Article
import com.mahmoud.tasknewapp.ui.NewsDetailsActivity
import com.mahmoud.tasknewapp.ui.SearchActivity

class NewsAdapter(context: Context) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        var article=differ.currentList[position]
        holder.itemView.apply {
           Glide.with(this).load(article.urlToImage).into((findViewById(R.id.ivArticleImage)))
            (findViewById<TextView>(R.id.tvTitle)).text=article.title
            (findViewById<TextView>(R.id.tvSource)).text=article.source.name

            setOnClickListener{
                val intent = Intent(context, NewsDetailsActivity::class.java)
                    intent.putExtra("title",article.title)
                    intent.putExtra("description",article.description)
                    intent.putExtra("imgUrl",article.urlToImage)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}