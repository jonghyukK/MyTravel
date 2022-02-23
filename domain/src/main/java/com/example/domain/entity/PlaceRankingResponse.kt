package com.example.domain.entity

/**
 * MyTravel
 * Class: PlaceRankingResponse
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
data class PlaceRankingResponse(
    val result: Boolean,
    val data : List<PlaceRanking> = listOf(),
    val errorMsg: String? = null
)