package com.example.beokweather.domain.entity

import com.squareup.moshi.Json

data class WeatherItemResponse(

    @field:Json(name = "icon")
    val icon: String? = null,

    @field:Json(name = "description")
    val description: String? = null,

    @field:Json(name = "main")
    val main: String? = null,

    @field:Json(name = "id")
    val id: Int? = null
)