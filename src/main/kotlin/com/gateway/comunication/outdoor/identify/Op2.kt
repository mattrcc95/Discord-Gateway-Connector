package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName

data class Op2(
    @SerializedName("token") val token: String = "",
    @SerializedName("properties") val connectionProperty: ConnectionProperty,
    @SerializedName("compress") val compress: Boolean? = null,
    @SerializedName("large_threshold") val largeThreshold: Int? = 250,
    @SerializedName("shard") val shard: List<Int>? = null,
    @SerializedName("presence") val presence: Presence?,
    @SerializedName("intents") val intents: Int,
)