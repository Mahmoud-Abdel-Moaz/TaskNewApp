package com.mahmoud.tasknewapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.tasknewapp.R
import com.mahmoud.tasknewapp.adapters.NewsAdapter
import com.mahmoud.tasknewapp.pojo.Article
import com.mahmoud.tasknewapp.repository.NewsRepository
import com.mahmoud.tasknewapp.util.Resource

class SearchActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var paginationProgressBar: ProgressBar
    lateinit var rvSearchNews: RecyclerView
    lateinit var etSearch: EditText
    lateinit var articles: List<Article>
    lateinit var searchResultArticles: List<Article>
    val TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        articles=ArrayList()
        searchResultArticles=ArrayList()
        paginationProgressBar=findViewById(R.id.paginationProgressBar)
        rvSearchNews=findViewById(R.id.rvSearchNews)
        etSearch=findViewById(R.id.etSearch)
        var repository = NewsRepository()
        var viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        setupRecyclerView()

        viewModel.breakingNews.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        //newsAdapter.differ.submitList(newsResponse.articles)
                        articles=newsResponse.articles
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error Happened: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                searchForArticles(s.toString())
            }
        })
    }

    private fun searchForArticles(word:String){
      /*  terms.all { term -> product.any { word -> word.contains(term) } }
        searchResultArticles=articles.all { article ->  article.title.contains(word)}*/
        searchResultArticles=ArrayList()
        articles.forEach { article ->
            if (article.title.contains(word)){
                searchResultArticles = searchResultArticles + article
            }
        }
        newsAdapter.differ.submitList(searchResultArticles)
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(this@SearchActivity)
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }
}