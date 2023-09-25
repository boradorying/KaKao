package com.example.kakaosearch.retrofit

import com.example.kakaosearch.data.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: NetworkInterface by lazy {
        retrofit.create(NetworkInterface::class.java)
    }
}
