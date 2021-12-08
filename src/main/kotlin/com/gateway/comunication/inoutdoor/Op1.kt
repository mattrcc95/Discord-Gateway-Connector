package com.gateway.comunication.inoutdoor

import com.google.gson.annotations.SerializedName

data class Op1(@SerializedName("op") val op: Int = 0, @SerializedName("d") val d: Int? = null)