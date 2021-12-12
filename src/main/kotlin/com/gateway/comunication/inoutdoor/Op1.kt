package com.gateway.comunication.inoutdoor

import com.gateway.comunication.indoor.GatewayIndoor
import com.gateway.comunication.outdoor.GatewayOutdoor
import com.google.gson.annotations.SerializedName

data class Op1(@SerializedName("op") val op: Int = 1, @SerializedName("d") val d: Int? = null): GatewayIndoor, GatewayOutdoor