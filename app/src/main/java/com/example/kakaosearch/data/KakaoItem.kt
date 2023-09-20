package com.example.kakaosearch.data

import com.google.gson.annotations.SerializedName

data class KakaoItem(

    val thumbnailUrl: String,
    val title: String,
    val datetime: String,

    val itemType: ItemType ,// 이미지와 동영상을 구분하는 필드
    var isHeart : Boolean = false
)

enum class ItemType {
    IMAGE,
    VIDEO
}
