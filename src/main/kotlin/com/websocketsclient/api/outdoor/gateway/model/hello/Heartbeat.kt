package com.websocketsclient.api.outdoor.gateway.model.hello

import com.google.gson.annotations.SerializedName

data class Heartbeat(
    @SerializedName("heartbeat_interval") val heartbeatInterval: Long,
    @SerializedName("_trace") val trace: List<String>?
)