package com.example.kakaosearch.data



data class KakaoItem(

    val thumbnailUrl: String,
    val title: String,
    val datetime: String,
    val itemType: ItemType ,// 이미지와 동영상을 구분하는 필드
    var isHeart : Boolean
)

enum class ItemType { //코드 가독성이랑 상수집합 정의 가능함
    IMAGE,
    VIDEO
}
