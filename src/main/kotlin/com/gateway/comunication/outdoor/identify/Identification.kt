package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName
import java.io.File

data class Identification(
    @SerializedName("token") val token: String = File("src/main/resources/botInfo.txt").useLines { it.toList() }[0].trim(),
    @SerializedName("properties") val connectionProperty: ConnectionProperty = ConnectionProperty(),
    @SerializedName("compress") val compress: Boolean? = null,
    @SerializedName("large_threshold") val largeThreshold: Int? = null,
    @SerializedName("shard") val shard: List<Int>? = null,
    @SerializedName("presence") val presence: Presence? = null,
    @SerializedName("intents") val intents: Int = 9,
)
