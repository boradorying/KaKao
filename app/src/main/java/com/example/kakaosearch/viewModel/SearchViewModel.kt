package com.example.kakaosearch.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaosearch.data.Constants
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KakaoItem
import com.example.kakaosearch.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchViewModel: ViewModel(){
    private val _searchResults =MutableLiveData<List<KakaoItem>>()
    val searchResults: LiveData<List<KakaoItem>> get() = _searchResults
    override fun onCleared() {
        super.onCleared()
        Log.d("lifecycle","viewModel : onCleared")
    }

     fun searchInfo(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageResponse = RetrofitInstance.getApiService()
                .searchImage(Constants.AUTH_KEY, query, "recency", 1, 80)
            val videoResponse = RetrofitInstance.getApiService()
                .searchVideo(Constants.AUTH_KEY, query, "recency", 1, 15)

            if (imageResponse.isSuccessful && videoResponse.isSuccessful) {
                val imageDocuments = imageResponse.body()?.documents
                val videoDocuments = videoResponse.body()?.documents

                if (imageDocuments != null && videoDocuments != null) {
                    val combinedList = mutableListOf<KakaoItem>()

                    // 이미지와 동영상을 KaKaoItem으로 통합하여 리스트에 추가
                    imageDocuments.forEach {
                        combinedList.add(KakaoItem(it.thumbnailUrl, it.displaySitename, it.datetime, ItemType.IMAGE,false))
                    }
                    videoDocuments.forEach {
                        combinedList.add(KakaoItem(it.thumbnailUrl, it.title, it.datetime, ItemType.VIDEO,false))
                    }
                    combinedList.sortByDescending { it.datetime }//시간역순정렬

                    withContext(Dispatchers.Main) {
                        _searchResults.postValue(combinedList)
                    }
                }
            }
        }
    }
}