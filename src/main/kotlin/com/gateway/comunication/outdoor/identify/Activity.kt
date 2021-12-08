package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("name") val name: String = "",
    @SerializedName("type") val type: Int = 4,
)
