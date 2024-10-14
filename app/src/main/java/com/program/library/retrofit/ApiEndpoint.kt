package com.program.library.retrofit

import com.program.library.HomeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("volumes")
    fun getBooks(@Query("q") query: String): Call<HomeModel>
}