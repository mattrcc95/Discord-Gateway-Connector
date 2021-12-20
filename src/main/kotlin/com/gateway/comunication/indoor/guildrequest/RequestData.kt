package com.gateway.comunication.indoor.guildrequest

import com.google.gson.annotations.SerializedName

class RequestData(
    @SerializedName("guild_id") val guildId: String,
    @SerializedName("query") val query: String? =  "b",
    @SerializedName("limit") val limit: Int = 100,
    @SerializedName("presences") val presences: Boolean? = false,
    @SerializedName("user_ids") val userIds: List<String>? = null,
    @SerializedName("nonce?") val nonce: String? = null
)
