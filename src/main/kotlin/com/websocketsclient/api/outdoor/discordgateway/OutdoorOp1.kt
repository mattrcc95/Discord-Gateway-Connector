package com.websocketsclient.api.outdoor.discordgateway

import com.google.gson.annotations.SerializedName

data class OutdoorOp1(@SerializedName("op") val op: Int = 1, @SerializedName("d") val d: Int? = null)