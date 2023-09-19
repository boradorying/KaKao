package com.example.kakaosearch.data

import com.google.gson.annotations.SerializedName

data class KakaoVideoResponse (
    @SerializedName("meta")
    val metaData: VideoMeata?,
    @SerializedName("documents")
    val documents : MutableList<KakaoVideo>?
)

data class VideoMeata (
    @SerializedName("total_count")
    val totalCount : Int?,
    @SerializedName("pageable_count")
    val pageale : Int?,
    @SerializedName("is_end")
    val isEnd:Boolean?
)
data class KakaoVideo (  //JSON으로 직렬화(객체를 JSON 문자열로 변환)

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("dateTime")
    val dateTime: String,//타임toconvert 해서 원하는 타임포맷으로 뿌려지는거
    var isHeart :Boolean =false
)