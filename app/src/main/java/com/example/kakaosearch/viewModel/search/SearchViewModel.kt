package com.example.kakaosearch.viewModel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaosearch.retrofit.Repository
import com.example.kakaosearch.data.ItemType
import com.example.kakaosearch.data.KakaoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<KakaoItem>>()
    val searchResults: LiveData<List<KakaoItem>> get() = _searchResults

    fun searchInfo(query: String) {
        val imageFlow = flow {
            val imageResponse = repository.searchImage(query)
            emit(imageResponse.body()?.documents ?: emptyList())
        }

        val videoFlow = flow {
            val videoResponse = repository.searchVideo(query)
            emit(videoResponse.body()?.documents ?: emptyList())
        }

        viewModelScope.launch(Dispatchers.IO) {
            combine(imageFlow, videoFlow) { imageDocuments, videoDocuments ->
                val combinedList = mutableListOf<KakaoItem>()

                imageDocuments.forEach {
                    combinedList.add(KakaoItem(it.thumbnailUrl, it.displaySitename, it.datetime, ItemType.IMAGE, false))
                }
                videoDocuments.forEach {
                    combinedList.add(KakaoItem(it.thumbnailUrl, it.title, it.datetime, ItemType.VIDEO, false))
                }
                combinedList.sortByDescending { it.datetime }


                _searchResults.postValue(combinedList)
            }.flowOn(Dispatchers.Default).collect()
        }
    }
}
