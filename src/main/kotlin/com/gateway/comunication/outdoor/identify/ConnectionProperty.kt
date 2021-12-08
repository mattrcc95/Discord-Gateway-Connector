package com.gateway.comunication.outdoor.identify

import com.google.gson.annotations.SerializedName

const val osDollar = "$" + "os"
const val browserDollar = "$" + "browser"
const val deviceDollar = "$" + "device"

data class ConnectionProperty(
    @SerializedName(osDollar) val os: String = "Windows",
    @SerializedName(browserDollar) val browser: String = "",
    @SerializedName(deviceDollar) val device: String = "",
)
