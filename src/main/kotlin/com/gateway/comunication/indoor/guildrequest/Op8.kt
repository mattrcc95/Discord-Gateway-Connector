package com.gateway.comunication.indoor.guildrequest

import com.gateway.comunication.indoor.GatewayIndoor
import com.google.gson.annotations.SerializedName

data class Op8(
    @SerializedName("op") val op: Int = 8,
    @SerializedName("d") val data: RequestData = RequestData(),
) : GatewayIndoor