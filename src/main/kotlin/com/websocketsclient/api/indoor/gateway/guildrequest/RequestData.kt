package com.websocketsclient.api.indoor.gateway.guildrequest

import com.google.gson.annotations.SerializedName

class RequestData(
    @SerializedName("guild_id") val guildId: String,
    @SerializedName("query") val query: String?,
    @SerializedName("limit") val limit: Int = 100,
    @SerializedName("presences") val presences: Boolean? = false,
    @SerializedName("user_ids") val userIds: List<String>? = null,
    @SerializedName("nonce?") val nonce: String? = null
)
