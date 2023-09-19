package com.example.kakaosearch.data

import com.google.gson.annotations.SerializedName

data class KakaoItem(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("datetime")
    val datetime: String,
    val itemType: ItemType // 이미지와 동영상을 구분하는 필드
)

enum class ItemType {
    IMAGE,
    VIDEO
}
