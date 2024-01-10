package com.example.kakaosearch.retrofit



import com.example.kakaosearch.data.KakaoImageResponse
import com.example.kakaosearch.data.KakaoVideoResponse
import retrofit2.Response

// Repository 인터페이스: 이미지 및 비디오 검색 결과를 가져오는 메서드
interface Repository {
    suspend fun searchImage(query: String): Response<KakaoImageResponse>
    suspend fun searchVideo(query: String): Response<KakaoVideoResponse>
}
//1. RetrofitInstance 객체:
//Retrofit 인스턴스를 초기화합니다. 기본 URL(Constants.BASE_URL)을 지정하고 JSON 응답을 파싱하기 위해 GsonConverterFactory를 추가합니다.
//apiService: NetworkInterface의 인스턴스를 첫 접근시 생성하고 반환하는 lazy 위임 속성입니다.
//2. NetworkInterface 인터페이스:
//비동기 네트워크 요청을 위한 두 개의 suspend 함수를 정의합니다:
//searchImage: 지정된 매개변수(인증 키, 쿼리, 정렬 순서, 페이지 번호, 페이지 크기)를 사용하여 이미지를 검색하고 Response<KakaoImageResponse>를 반환합니다.
//searchVideo: searchImage와 유사하지만 비디오 검색에 대해 Response<KakaoVideoResponse>를 반환합니다.
//3. Repository 인터페이스:
//구현해야 할 두 개의 suspend 함수를 정의합니다:
//searchImage(query: String): Response<KakaoImageResponse>를 반환해야 합니다.
//searchVideo(query: String): Response<KakaoVideoResponse>를 반환해야 합니다.
//4. RepositoryImpl 클래스:
//Repository 인터페이스를 구현합니다.
//NetworkInterface 인스턴스를 초기화합니다.
//Repository 인터페이스에서 searchImage 및 searchVideo 함수를 각각 구현하여, 특정 매개변수를 사용하여 NetworkInterface 메소드를 호출합니다.
//5. KakaoImageResponse, ImageMeta, KaKaoImage 데이터 클래스:
//이미지 검색에 대한 예상 JSON 응답의 구조를 정의합니다.
//KakaoImageResponse: 메타데이터와 이미지 목록을 포함합니다.
//ImageMeta: 검색 결과에 대한 메타데이터를 포함합니다.
//KaKaoImage: 개별 이미지의 데이터 구조를 정의하며, JSON 키에 매핑되는 직렬화된 이름을 포함합니다.
//6. SearchViewModelFactory 클래스:
//Repository 인스턴스를 종속성으로 가진 SearchViewModel을 인스턴스화하기 위한 사용자 정의 ViewModelProvider.Factory입니다.
//Repository 종속성을 전달하여 SearchViewModel의 인스턴스를 반환하는 create 함수를 재정의합니다.
//제공된 modelClass가 SearchViewModel에서 파생 가능한 경우 IllegalArgumentException을 발생시킵니다.
//작업 흐름 개요:
//RetrofitInstance는 NetworkInterface 인스턴스를 제공합니다.
//RepositoryImpl은 NetworkInterface를 사용하여 네트워크 요청을 수행하고 Repository 인터페이스를 구현합니다.
//SearchViewModelFactory는 Repository를 종속성으로 가진 SearchViewModel 인스턴스를 생성하고 제공합니다.
//데이터 클래스들(KakaoImageResponse, ImageMeta, KaKaoImage)은 예상 응답의 구조를 정의하여 JSON 응답을 Kotlin 객체로 역직렬화를 돕습니다.
//주의사항:
//build.gradle에 필요한 종속성(Retrofit, Gson, ViewModel 등)을 추가해야 합니다.
//Constants 클래스에 BASE_URL과 AUTH_KEY가 올바르게 정의되어 있는지 확인하세요.
//ViewModel 및 해당 UI 컨트롤러(Activity 또는 Fragment 등)에서 네트워크 응답, 오류 및 UI 업데이트를 처리하세요.
//“randomdmdmdm”과 같은 플레이스홀더 대신 적절한 오류 메시지를 추가하는 것을 고려하세요.
//결론:
//이 코드는 ViewModel 인스턴스를 관찰하고 받은 데이터를 기반으로 UI를 업데이트하는 Android Activity 또는 Fragment와 같은 더 큰 아키텍처의 일부입니다. 견고한 구현을 위해 생명주기와 데이터 흐름을 적절하게 처리하세요.