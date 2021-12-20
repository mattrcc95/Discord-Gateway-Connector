package com.gateway.comunication.indoor.guildrequest

import com.google.gson.annotations.SerializedName

class RequestData(
//  @SerializedName("guild_id") val guildId: String = "902874779613282364",
    @SerializedName("guild_id") val guildId: String = "907344205653893230",
//  @SerializedName("guild_id") val guildId: String = "879869697548505108",
    @SerializedName("query") val query: String? = "Animal",
    @SerializedName("limit") val limit: Int = 100,
    @SerializedName("presences") val presences: Boolean? = true,
    @SerializedName("user_ids") val userIds: List<String>? = null,
    @SerializedName("nonce?") val nonce: String? = null
)
