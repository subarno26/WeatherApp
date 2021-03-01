package com.example.growthpoc.repository

import android.content.ClipDescription
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepo @Inject constructor (context: Context){
    private val datastore = context.createDataStore(
            name = "Weather_prefs"
    )

    companion object{
        val temp_key = doublePreferencesKey("temp")
        val pressure_key= intPreferencesKey("pressure")
        val humidity_key = intPreferencesKey("humidity")
        val temp_min_key= doublePreferencesKey("temp_min")
        val temp_max_key = doublePreferencesKey("temp_max")
        val main_key = stringPreferencesKey("main")
        val description_key = stringPreferencesKey("description")
        val icon_key = stringPreferencesKey("icon")
    }

    suspend fun saveToDataStore(temp: Double, pressure: Int, humidity: Int, temp_min: Double, temp_max: Double, main : String, description : String, icon: String){
        datastore.edit {
            it[temp_key] = temp
            it[pressure_key] = pressure
            it[humidity_key] = humidity
            it[temp_min_key] = temp_min
            it[temp_max_key] = temp_max
            it[main_key] = main
            it[description_key] = description
            it[icon_key] = icon
        }
    }

    val tempFlow = datastore.data.map {
        it[temp_key] ?: 0.0
    }

    val tempMinFlow = datastore.data.map {
        it[temp_min_key] ?: 0.0
    }
    val tempMaxFlow = datastore.data.map {
        it[temp_max_key] ?: 0.0
    }
    val pressureFlow = datastore.data.map {
        it[pressure_key] ?: 0
    }
    val humidityFlow = datastore.data.map {
        it[humidity_key] ?: 0
    }
    val mainFlow = datastore.data.map {
        it[main_key] ?: ""
    }

    val descriptionFlow = datastore.data.map {
        it[description_key] ?: ""
    }

    val iconFlow = datastore.data.map {
        it[icon_key] ?: ""
    }

}