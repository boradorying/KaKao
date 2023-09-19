package com.example.kakaosearch.retrofit

import com.example.kakaosearch.data.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService():NetWorkInterface{
        return getRetrofit().create(NetWorkInterface::class.java)
    }
}