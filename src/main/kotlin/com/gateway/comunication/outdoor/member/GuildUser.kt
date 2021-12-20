package com.gateway.comunication.outdoor.member

import com.google.gson.annotations.SerializedName

data class GuildUser(
    @SerializedName("username") val username: String,
    @SerializedName("id") val userId: String,
    @SerializedName("discriminator") val discriminator: String,
    @SerializedName("avatar") val avatar: String,
)
