package com.gateway.application.comunication.indoor.identify

import com.gateway.application.Utils.botInfo
import com.google.gson.annotations.SerializedName

data class Identification(
    @SerializedName("token") val token: String = botInfo.useLines { it.toList() }[0].trim(),
    @SerializedName("intents") val intents: Int = 258,
    @SerializedName("properties") val connectionProperty: ConnectionProperty = ConnectionProperty(),
    @SerializedName("compress") val compress: Boolean? = null,
    @SerializedName("large_threshold") val largeThreshold: Int? = null,
    @SerializedName("shard") val shard: List<Int>? = null,
    @SerializedName("presence") val presence: Presence? = null,
)
