package com.websocketsclient.api.outdoor.discordgateway.model.ready

import com.google.gson.annotations.SerializedName

data class ReadyAttributes(
    @SerializedName("v") val v: String,
    @SerializedName("user") val user: UserProperties,
    @SerializedName("guilds") val unavailableGuidls: List<UnavailableGuilds>,
    @SerializedName("session_id") val sessionId: String,
    @SerializedName("shard") val shard: List<Int>?,
    @SerializedName("application") val application: ApplicationDetails
)
