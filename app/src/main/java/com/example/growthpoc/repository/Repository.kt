package com.example.growthpoc.repository

import com.example.growthpoc.network.Api
import com.example.growthpoc.utils.Constants
import com.example.growthpoc.models.WeatherModel
import retrofit2.Call
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api) {
    fun getResponse() : Call<WeatherModel> = api.getResponse("New Delhi", Constants.API_KEY,"metric")
}