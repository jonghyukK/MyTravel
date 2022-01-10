package org.kjh.mytravel

/**
 * MyTravel
 * Class: CityItem
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

data class PostItem(
    val postId      : Long,
    val writer      : String,
    val content     : String,
    val date        : String,
    val cityName    : String,
    val placeAddress: String,
    val placeName   : String,
    val profileImg  : Int,
    val postImages  : List<Int>
)

val tempPostItemList = listOf(
    PostItem(
        11,
        "saz300@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-05",
        "서울",
        "서울시 중구 세종대로 110",
        "서울시청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul)
    ),
    PostItem(
        12,
        "saz400@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-03",
        "경기도",
        "경기 수원시 팔달구 효원로 1",
        "경기도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi)
    ),
    PostItem(
        13,
        "saz500@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-02",
        "충청도",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청남도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo)
    ),
    PostItem(
        14,
        "saz600@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-01",
        "전라도",
        "전북 전주시 완산구 효자로 225",
        "전라도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado)
    ),
    PostItem(
        15,
        "saz700@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-11",
        "강원도",
        "강원 춘천시 중앙로 1",
        "강원도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo)
    ),
    PostItem(
        16,
        "saz800@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-12",
        "경상도",
        "경북 안동시 풍천면 도청대로 455",
        "경상도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo)
    ),
    PostItem(
        17,
        "saz900@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-15",
        "제주도",
        "제주 제주시 문연로 6",
        "제주도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju)
    ),
    PostItem(
        18,
        "saz300@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-05",
        "서울",
        "서울시 중구 세종대로 110",
        "서울시청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul)
    ),
    PostItem(
        19,
        "saz400@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-03",
        "경기도",
        "경기 수원시 팔달구 효원로 1",
        "경기도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi)
    ),
    PostItem(
        20,
        "saz500@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-02",
        "충청도",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청남도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo)
    ),
)

data class PlaceItem(
    val placeId      : Long,
    val placeName    : String,
    val placeAddress : String,
    val cityName     : String,
    val placeImg     : Int,
    val prevImgList  : List<Int>,
    val postItemList : List<PostItem>
)

val tempPlaceItemList = listOf(
    PlaceItem(
        111,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        R.drawable.temp_img_seoul,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItem(
        112,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        R.drawable.temp_img_gyeonggi,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItem(
        113,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        R.drawable.temp_img_chungcheongdo,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItem(
        114,
        "전라도청",
        "전북 전주시 완산구 효자로 225",
        "전라도",
        R.drawable.temp_img_jeonrado,
        listOf(R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado),
        tempPostItemList
    ),
    PlaceItem(
        115,
        "강원도청",
        "강원 춘천시 중앙로 1",
        "강원도",
        R.drawable.temp_img_gangwondo,
        listOf(R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo),
        tempPostItemList
    ),
    PlaceItem(
        116,
        "경상도청",
        "경북 안동시 풍천면 도청대로 455",
        "경상도",
        R.drawable.temp_img_gyeongsangdo,
        listOf(R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo),
        tempPostItemList
    ),
    PlaceItem(
        117,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        R.drawable.temp_img_jeju,
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItem(
        118,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        R.drawable.temp_img_seoul,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItem(
        119,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        R.drawable.temp_img_gyeonggi,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItem(
        120,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        R.drawable.temp_img_chungcheongdo,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItem(
        121,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        R.drawable.temp_img_jeju,
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItem(
        122,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        R.drawable.temp_img_seoul,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItem(
        123,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        R.drawable.temp_img_gyeonggi,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItem(
        124,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        R.drawable.temp_img_chungcheongdo,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
)

data class CityItem(
    val cityId: Long,
    val cityName: String,
    val cityDesc: String,
    val cityImg: Int
)

val cityItemList = listOf(
    CityItem(1,"서울", "대한민국의 수도 서울!", R.drawable.temp_img_seoul),
    CityItem(2,"경기도", "서울을 둘러싸고 있는 경기도!", R.drawable.temp_img_gyeonggi),
    CityItem(3,"충청도", "여기는 충청도!", R.drawable.temp_img_chungcheongdo),
    CityItem(4, "전라도", "여기는 전라도!", R.drawable.temp_img_jeonrado),
    CityItem(5, "강원도", "여기는 강원도!", R.drawable.temp_img_gangwondo),
    CityItem(6, "경상도", "여기는 경상도!", R.drawable.temp_img_gyeongsangdo),
    CityItem(7, "제주도", "여기는 제주도!", R.drawable.temp_img_jeju)
)



data class EventItem(
    val id: Long,
    val bigTitle: String,
    val itemList: List<PlaceItem>
)

val eventItemList = listOf(
    EventItem(
        id = 1111,
        bigTitle = "서울, 여기 가보는 건 어때요?",
        itemList = tempPlaceItemList
    ),
    EventItem(
        id = 2222,
        bigTitle = "인천의 핫한 곳은 여기!!",
        itemList = tempPlaceItemList
    )
)



data class WholeCityItem(
    val categoryName: String,
    val items: List<CityItem>
)

val wholeCityList = listOf(
    WholeCityItem(
        categoryName = "서울",
        items = listOf(
            CityItem(11, "서울", "대한민쿡의 수도 서울!", R.drawable.temp_img_seoul),
            CityItem(12, "서울", "대한민쿡의 수도 서울!", R.drawable.temp_img_seoul),
            CityItem(13, "서울", "대한민쿡의 수도 서울!", R.drawable.temp_img_seoul),
            CityItem(14, "서울", "대한민쿡의 수도 서울!", R.drawable.temp_img_seoul),
            CityItem(15, "서울", "대한민쿡의 수도 서울!", R.drawable.temp_img_seoul),
        )
    ),
    WholeCityItem(
        categoryName = "경기도",
        items = listOf(
            CityItem(21, "경기도", "대한민쿡의 수도 경기도!", R.drawable.temp_img_gyeonggi),
            CityItem(22, "경기도", "대한민쿡의 수도 경기도!", R.drawable.temp_img_gyeonggi),
            CityItem(23, "경기도", "대한민쿡의 수도 경기도!", R.drawable.temp_img_gyeonggi),
            CityItem(24, "경기도", "대한민쿡의 수도 경기도!", R.drawable.temp_img_gyeonggi),
            CityItem(25, "경기도", "대한민쿡의 수도 경기도!", R.drawable.temp_img_gyeonggi),
        )
    ),
    WholeCityItem(
        categoryName = "충청도",
        items = listOf(
            CityItem(31, "충청도", "대한민쿡의 수도 충청도!", R.drawable.temp_img_chungcheongdo),
            CityItem(32, "충청도", "대한민쿡의 수도 충청도!", R.drawable.temp_img_chungcheongdo),
            CityItem(33, "충청도", "대한민쿡의 수도 충청도!", R.drawable.temp_img_chungcheongdo),
            CityItem(34, "충청도", "대한민쿡의 수도 충청도!", R.drawable.temp_img_chungcheongdo),
            CityItem(35, "충청도", "대한민쿡의 수도 충청도!", R.drawable.temp_img_chungcheongdo),
        )
    ),
    WholeCityItem(
        categoryName = "전라도",
        items = listOf(
            CityItem(41, "전라도", "대한민쿡의 수도 전라도!", R.drawable.temp_img_jeonrado),
            CityItem(42, "전라도", "대한민쿡의 수도 전라도!", R.drawable.temp_img_jeonrado),
            CityItem(43, "전라도", "대한민쿡의 수도 전라도!", R.drawable.temp_img_jeonrado),
            CityItem(44, "전라도", "대한민쿡의 수도 전라도!", R.drawable.temp_img_jeonrado),
            CityItem(45, "전라도", "대한민쿡의 수도 전라도!", R.drawable.temp_img_jeonrado),
        )
    ),
    WholeCityItem(
        categoryName = "강원도",
        items = listOf(
            CityItem(51, "강원도", "대한민쿡의 수도 강원도!", R.drawable.temp_img_gangwondo),
            CityItem(52, "강원도", "대한민쿡의 수도 강원도!", R.drawable.temp_img_gangwondo),
            CityItem(53, "강원도", "대한민쿡의 수도 강원도!", R.drawable.temp_img_gangwondo),
            CityItem(54, "강원도", "대한민쿡의 수도 강원도!", R.drawable.temp_img_gangwondo),
            CityItem(55, "강원도", "대한민쿡의 수도 강원도!", R.drawable.temp_img_gangwondo),
        )
    ),
    WholeCityItem(
        categoryName = "경상도",
        items = listOf(
            CityItem(61, "경상도", "대한민쿡의 수도 경상도!", R.drawable.temp_img_gyeongsangdo),
            CityItem(62, "경상도", "대한민쿡의 수도 경상도!", R.drawable.temp_img_gyeongsangdo),
            CityItem(63, "경상도", "대한민쿡의 수도 경상도!", R.drawable.temp_img_gyeongsangdo),
            CityItem(64, "경상도", "대한민쿡의 수도 경상도!", R.drawable.temp_img_gyeongsangdo),
            CityItem(65, "경상도", "대한민쿡의 수도 경상도!", R.drawable.temp_img_gyeongsangdo),
        )
    ),
    WholeCityItem(
        categoryName = "제주도",
        items = listOf(
            CityItem(71, "제주도", "대한민쿡의 수도 제주도!", R.drawable.temp_img_jeju),
            CityItem(72, "제주도", "대한민쿡의 수도 제주도!", R.drawable.temp_img_jeju),
            CityItem(73, "제주도", "대한민쿡의 수도 제주도!", R.drawable.temp_img_jeju),
            CityItem(74, "제주도", "대한민쿡의 수도 제주도!", R.drawable.temp_img_jeju),
            CityItem(75, "제주도", "대한민쿡의 수도 제주도!", R.drawable.temp_img_jeju),
        )
    ),
)

