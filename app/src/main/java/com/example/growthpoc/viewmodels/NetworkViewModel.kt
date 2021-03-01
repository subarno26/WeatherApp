package com.example.growthpoc.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.growthpoc.repository.Repository
import com.example.growthpoc.models.WeatherModel
import com.example.growthpoc.repository.DataStoreRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkViewModel @Inject constructor(private val repository: Repository,
                                           private val dataRepository: DataStoreRepo) : ViewModel() {
    private val TAG = "NetworkViewModel"

    var myResponse: MutableLiveData<WeatherModel> = MutableLiveData()

    val temp = dataRepository.tempFlow.asLiveData()
    val pressure = dataRepository.pressureFlow.asLiveData()
    val humidity = dataRepository.humidityFlow.asLiveData()
    val temp_max = dataRepository.tempMaxFlow.asLiveData()
    val temp_min = dataRepository.tempMinFlow.asLiveData()
    val main = dataRepository.mainFlow.asLiveData()
    val description = dataRepository.descriptionFlow.asLiveData()
    val icon = dataRepository.iconFlow.asLiveData()

    fun getResponse() : MutableLiveData<WeatherModel>{
        val call = repository.getResponse()
        call.enqueue(object : Callback<WeatherModel> {


            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                myResponse.value = response.body()
                saveResponse(response.body())
                Log.d(TAG, "onResponse: Successful")
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.toString()}", )
            }

        })
        return myResponse

    }

    private fun saveResponse(response: WeatherModel?) = viewModelScope.launch {
        val temp = response!!.main.temp
        val pressure = response.main.pressure
        val humidity = response.main.humidity
        val minTemp = response.main.temp_min
        val maxTemp = response.main.temp_max
        val main = response.weather[0].main
        val description = response.weather[0].description
        val icon = response.weather[0].icon

        dataRepository.saveToDataStore(temp,pressure,humidity,minTemp,maxTemp,main, description, icon)
    }


}