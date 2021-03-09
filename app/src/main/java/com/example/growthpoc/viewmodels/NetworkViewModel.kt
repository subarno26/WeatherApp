package com.example.growthpoc.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.growthpoc.repository.Repository
import com.example.growthpoc.models.response.WeatherModel
import com.example.growthpoc.repository.DataStoreRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NetworkViewModel @Inject constructor(private val repository: Repository,
                                           private val dataRepository: DataStoreRepo,
                                           private val context: Context) : ViewModel() {
    private val TAG = "NetworkViewModel"
    private val INTERNET = "Internet"
    private var _myResponse: MutableLiveData<WeatherModel> = MutableLiveData()
    val myResponse: LiveData<WeatherModel>
        get() = _myResponse

    private var _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected


    val temp = dataRepository.tempFlow.asLiveData()
    val main = dataRepository.mainFlow.asLiveData()
    val icon = dataRepository.iconFlow.asLiveData()
    val name = dataRepository.nameFlow.asLiveData()
    val timestamp = dataRepository.timestampFlow.asLiveData()

    fun callWeatherApi(cityName: String) {
        val call = repository.getResponse(cityName)
        call.enqueue(object : Callback<WeatherModel> {

            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                _myResponse.value = response.body()
                saveResponse(response.body())
                Log.d(TAG, "onResponse: Successful")
//                Log.d(TAG, "onResponse: ${response.body().toString()}")
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}",)
            }

        })
    }

    private fun saveResponse(response: WeatherModel?) = viewModelScope.launch {
        try {
            val temp = response!!.main.temp
            val main = response.weather[0].main
            val icon = response.weather[0].icon
            val name = response.name

            val df = SimpleDateFormat("MMM dd, h:mm:ss a")
            val currentTime = df.format(Calendar.getInstance().time)
            val timestamp = "Last updated at: $currentTime"

            dataRepository.saveToDataStore(temp, main, icon, name, timestamp)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

    }

     fun checkNetworkStatus(){
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val capabilities =
             connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
         if (capabilities != null) {
             if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                 _isConnected.postValue(true)
                 Log.i(INTERNET, "NetworkCapabilities.TRANSPORT_CELLULAR ${_isConnected.value.toString()}")}
             else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                 Log.i(INTERNET, "NetworkCapabilities.TRANSPORT_WIFI")
                 _isConnected.postValue(true)
             } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                 Log.i(INTERNET, "NetworkCapabilities.TRANSPORT_ETHERNET")
                 _isConnected.value =  true
             }
         }
         else {
             _isConnected.value = false
         }

    }
}