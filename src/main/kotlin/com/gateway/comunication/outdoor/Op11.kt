package com.gateway.comunication.outdoor

import com.google.gson.annotations.SerializedName

data class Op11(
    @SerializedName("op") val op: Int
) : GatewayOutdoor
