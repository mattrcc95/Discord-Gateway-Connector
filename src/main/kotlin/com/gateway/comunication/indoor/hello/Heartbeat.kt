package com.gateway.comunication.indoor.hello

import com.google.gson.annotations.SerializedName

data class Heartbeat(
    @SerializedName("heartbeat_interval") val heartbeatInterval: Long = 0L,
    @SerializedName("_trace") val trace: List<String>? = null
)