package com.example.growthpoc.models

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("id")val id: Int,
                @SerializedName("name")val name: String,
                @SerializedName("username") val uName: String,
                @SerializedName("email") val email: String)