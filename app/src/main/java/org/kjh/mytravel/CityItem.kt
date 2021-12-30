package org.kjh.mytravel

/**
 * MyTravel
 * Class: CityItem
 * Created by mac on 2021/12/22.
 *
 * Description:
 */
data class CityItem(
    val id: Long,
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