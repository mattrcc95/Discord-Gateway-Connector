package com.websocketsclient.api.indoor.gateway.identify

import com.google.gson.annotations.SerializedName

data class Op2(
    @SerializedName("op") val op: Int = 2,
    @SerializedName("d") val identification: Identification = Identification()
)