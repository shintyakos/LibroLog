package com.example.liberolog.api

import com.example.liberolog.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookSearchService {
    @GET(" ")
    fun getSearchBooks(
        @Query("q") isbn: String? = null,
        @Query("q") title: String? = null,
        @Query("key") key: String = BuildConfig.API_KEY,
    ): Call<GoogleBookSearchResponse>
}
