package com.mahmoud.tasknewapp.repository

import com.mahmoud.tasknewapp.api.RetrofitInstance

class NewsRepository {

    suspend fun
            getBreakingNews()=
        RetrofitInstance.api.getBreakingNews("eg")
}