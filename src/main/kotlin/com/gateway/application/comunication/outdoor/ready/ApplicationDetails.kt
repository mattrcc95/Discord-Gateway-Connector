package com.gateway.application.comunication.outdoor.ready

import com.google.gson.annotations.SerializedName

data class ApplicationDetails(
    @SerializedName("id") val applicationId: String,
    @SerializedName("flags") val flags: Int,
)
