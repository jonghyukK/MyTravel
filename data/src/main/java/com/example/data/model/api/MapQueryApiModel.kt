package com.example.data.model.api

import com.example.data.model.MapQueryModel
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: MapQueryApiModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class MapQueryApiModel(
    @SerializedName("documents")
    val mapQueries: List<MapQueryModel>
)