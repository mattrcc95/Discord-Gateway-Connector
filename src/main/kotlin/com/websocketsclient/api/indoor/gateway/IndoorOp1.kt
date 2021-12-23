package com.websocketsclient.api.indoor.gateway

import com.google.gson.annotations.SerializedName

data class IndoorOp1(@SerializedName("op") val op: Int = 1, @SerializedName("d") val d: Int? = null)