package com.example.kakaosearch.retrofit



import com.example.kakaosearch.data.KakaoImageResponse
import com.example.kakaosearch.data.KakaoVideoResponse
import retrofit2.Response

// Repository 인터페이스: 이미지 및 비디오 검색 결과를 가져오는 메서드
interface Repository {
    suspend fun searchImage(query: String): Response<KakaoImageResponse>
    suspend fun searchVideo(query: String): Response<KakaoVideoResponse>
}
