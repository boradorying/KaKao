package com.example.kakaosearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaosearch.data.KakaoItem

class BookmarkViewModel : ViewModel(){
    private val _bookmarkedItems : MutableLiveData<MutableList<KakaoItem>> = MutableLiveData(mutableListOf())
    val  bookmarkedItems : LiveData<MutableList<KakaoItem>> get() = _bookmarkedItems  //읽기전용

    fun addBookmark(item: KakaoItem){
        val items = _bookmarkedItems.value?: mutableListOf()
        if (!items.contains(item)){
            items.add(0,item)//맨앞에 추가임
            _bookmarkedItems.postValue(items)

        }
    }

    fun removeBookmark(item:KakaoItem){
        val items = _bookmarkedItems.value?: mutableListOf()
        if (items.contains(item))
            items.remove(item)
        _bookmarkedItems.postValue(items)
    }
}
