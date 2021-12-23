package com.httpclient.api.indoor

import com.google.gson.annotations.SerializedName

data class CreateDMChannelpayload(
 @SerializedName("recipient_id") val id: String
)