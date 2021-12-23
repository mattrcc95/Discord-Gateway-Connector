package com.websocketsclient.api.outdoor.gateway.model.ready

import com.google.gson.annotations.SerializedName

data class Ready(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val attributes: ReadyAttributes
)