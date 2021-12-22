package com.gateway.application.comunication.outdoor.hello

import com.google.gson.annotations.SerializedName

data class Op10(
    @SerializedName("op") val op: Int,
    @SerializedName("d") val d: Heartbeat,
    @SerializedName("s") val s: Int?,
    @SerializedName("t") val t: Int?,
)
