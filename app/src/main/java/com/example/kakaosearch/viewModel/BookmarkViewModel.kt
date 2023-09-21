package com.example.kakaosearch.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaosearch.data.KakaoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookmarkViewModel : ViewModel() {
    private val _bookmarkedItems: MutableLiveData<MutableList<KakaoItem>> =
        MutableLiveData(mutableListOf())
    val bookmarkedItems: LiveData<MutableList<KakaoItem>> get() = _bookmarkedItems  //읽기전용

    fun addBookmark(item: KakaoItem) {
        val items = _bookmarkedItems.value ?: mutableListOf()
        if (!items.contains(item)) {
            items.add(0, item)//맨앞에 추가임
            _bookmarkedItems.value = items
        }
    }

    fun removeBookmark(item: KakaoItem) {
        val items = _bookmarkedItems.value ?: mutableListOf()
        if (items.contains(item))
            items.remove(item)
        _bookmarkedItems.value = items
    }

    fun save(context: Context) {
        val pref = context.getSharedPreferences("pref", 0)
        val edit = pref.edit()
        val gson = Gson()
        val json = gson.toJson(_bookmarkedItems.value)//
        edit.putString("bookmark", json)
        edit.apply()
    }

    fun load(context: Context) {
        val pref = context.getSharedPreferences("pref", 0)
        val json = pref.getString("bookmark", "")
        if (json != null) {
            if (json.isNotEmpty()) {
                val gson = Gson()
                val type = object : TypeToken<MutableList<KakaoItem>>() {}.type
                _bookmarkedItems.postValue(gson.fromJson(json, type))
            }
        }
    }
}