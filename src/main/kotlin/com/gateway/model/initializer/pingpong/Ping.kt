package com.gateway.model.initializer.pingpong

import com.google.gson.annotations.SerializedName

data class Ping(@SerializedName("op") val op: Int = 1, @SerializedName("d") val d: Int? = null)