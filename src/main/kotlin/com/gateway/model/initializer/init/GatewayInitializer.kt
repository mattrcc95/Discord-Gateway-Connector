package com.gateway.model.initializer.init

import com.google.gson.annotations.SerializedName

data class GatewayInitializer(
    @SerializedName("op") val op: Int = 0,
    @SerializedName("d") val d: Heartbeat = Heartbeat(),
    @SerializedName("s") val s: Int? = null,
    @SerializedName("t") val t: Int? = null,
)