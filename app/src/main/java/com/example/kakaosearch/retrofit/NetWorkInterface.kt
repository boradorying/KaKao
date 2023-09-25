package com.example.kakaosearch.retrofit




import com.example.kakaosearch.data.Constants
import com.example.kakaosearch.data.KakaoImageResponse
import com.example.kakaosearch.data.KakaoVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkInterface {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Header("Authorization") apiKey: String = Constants.AUTH_KEY,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoImageResponse>

    @GET("v2/search/vclip")
    suspend fun searchVideo(
        @Header("Authorization") apiKey: String = Constants.AUTH_KEY,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoVideoResponse>
}
