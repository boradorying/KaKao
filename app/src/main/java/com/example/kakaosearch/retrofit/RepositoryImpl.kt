package com.example.kakaosearch.retrofit

import com.example.kakaosearch.data.Constants
import com.example.kakaosearch.data.KakaoImageResponse
import com.example.kakaosearch.data.KakaoVideoResponse

import retrofit2.Response

class RepositoryImpl(private val networkInterface: NetworkInterface) : Repository {
    override suspend fun searchImage(query: String): Response<KakaoImageResponse> {
        return networkInterface.searchImage(Constants.AUTH_KEY, query, "recency", 1, 80)
    }

    override suspend fun searchVideo(query: String): Response<KakaoVideoResponse> {
        return networkInterface.searchVideo(Constants.AUTH_KEY, query, "recency", 1, 15)
    }
}