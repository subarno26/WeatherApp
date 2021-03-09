package com.example.growthpoc.models.response

data class WeatherModel (
        val main : Main,
        val weather : List<Weather>,
        val name: String,
        val visibility: Int,
        val wind: Wind,
        val cod: Int
)
