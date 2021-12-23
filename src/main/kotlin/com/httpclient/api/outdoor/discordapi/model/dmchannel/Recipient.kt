package com.httpclient.api.outdoor.discordapi.model.dmchannel

import com.google.gson.annotations.SerializedName

data class Recipient (
    @SerializedName("id") val userId: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("discriminator") val discriminator: String,
    @SerializedName("public_flags") val publicFlags: Int
)
