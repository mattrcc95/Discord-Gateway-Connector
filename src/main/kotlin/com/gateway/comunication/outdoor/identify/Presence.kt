package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName

data class Presence(
    @SerializedName("since") val since: Int? = null,
    @SerializedName("activities") val activities: List<Activity> = emptyList(),
    @SerializedName("status") val status: String = "online",
    @SerializedName("afk") val afk: Boolean = true,
)
