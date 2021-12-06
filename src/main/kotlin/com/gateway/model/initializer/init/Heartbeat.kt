package com.gateway.model.initializer.init

import com.google.gson.annotations.SerializedName

data class Heartbeat(
    @SerializedName("heartbeat_interval") val heartbeatInterval: Long = 0,
    @SerializedName("_trace") val trace: List<String>? = null
)