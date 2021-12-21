package com.gateway.comunication.indoor.guildrequest

import com.google.gson.annotations.SerializedName

data class Op8(
    @SerializedName("op") val op: Int = 8,
    @SerializedName("d") val data: RequestData,
)