package com.gateway.application.comunication.indoor.identify

import com.google.gson.annotations.SerializedName

data class Presence(
    @SerializedName("since") val since: Long? = null,
    @SerializedName("activities") val activities: List<Activity> = emptyList(),
    @SerializedName("status") val status: String = "online",
    @SerializedName("afk") val afk: Boolean = false,
)
