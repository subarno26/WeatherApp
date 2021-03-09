package com.example.growthpoc.repository

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepo @Inject constructor (context: Context){
    private val datastore = context.createDataStore(
            name = "Weather_prefs"
    )

    companion object{
        val temp_key = doublePreferencesKey("temp")
        val main_key = stringPreferencesKey("main")
        val icon_key = stringPreferencesKey("icon")
        val name_key = stringPreferencesKey("name")
        val timestamp_key = stringPreferencesKey("timestamp")

    }

    suspend fun saveToDataStore(temp: Double, main : String, icon: String, name: String, timestamp: String){
        datastore.edit {
            it[temp_key] = temp
            it[main_key] = main
            it[icon_key] = icon
            it[name_key] = name
            it[timestamp_key] = timestamp
        }
    }

    val tempFlow = datastore.data.map {
        it[temp_key] ?: 0.0
    }
    val mainFlow = datastore.data.map {
        it[main_key] ?: ""
    }
    val iconFlow = datastore.data.map {
        it[icon_key] ?: ""
    }
    val nameFlow = datastore.data.map {
        it[name_key] ?: ""
    }
    val timestampFlow = datastore.data.map {
        it[timestamp_key] ?: ""
    }

}