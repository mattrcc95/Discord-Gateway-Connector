package com.gateway.application.comunication.inoutdoor

import com.google.gson.annotations.SerializedName

data class Op1(@SerializedName("op") val op: Int = 1, @SerializedName("d") val d: Int? = null)