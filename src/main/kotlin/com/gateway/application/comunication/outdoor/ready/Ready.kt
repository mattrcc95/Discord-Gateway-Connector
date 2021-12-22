package com.gateway.application.comunication.outdoor.ready

import com.google.gson.annotations.SerializedName

data class Ready(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val attributes: ReadyAttributes
)