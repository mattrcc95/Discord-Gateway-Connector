package com.gateway.comunication.indoor.ready

import com.google.gson.annotations.SerializedName

data class ReadyAttributes(
    @SerializedName("v") val v: String,
    @SerializedName("user") val user: UserProperties,
    @SerializedName("guilds") val unavailbaleGuidls: List<UnavailableGuilds>,
    @SerializedName("session_id") val sessionId: String,
    @SerializedName("shard") val shard: List<Int>?,
    @SerializedName("application") val application: ApplicationDetails
)
