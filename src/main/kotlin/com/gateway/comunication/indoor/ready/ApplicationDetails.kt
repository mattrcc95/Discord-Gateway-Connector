package com.gateway.comunication.indoor.ready

import com.google.gson.annotations.SerializedName

data class ApplicationDetails(
    @SerializedName("id") val applicationId: String = "",
    @SerializedName("flags") val flags: Int? = null,
)
