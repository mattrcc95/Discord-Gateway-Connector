package com.gateway.comunication.indoor.identify

import com.gateway.comunication.indoor.GatewayIndoor
import com.google.gson.annotations.SerializedName

data class Op2(
    @SerializedName("op") val op: Int = 2,
    @SerializedName("d") val identification: Identification = Identification()
) : GatewayIndoor