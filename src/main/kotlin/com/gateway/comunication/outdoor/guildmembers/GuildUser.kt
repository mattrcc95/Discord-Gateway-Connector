package com.gateway.comunication.outdoor.guildmembers

import com.gateway.connection.SingletonConnection
import com.google.gson.annotations.SerializedName

data class GuildUser(
    @SerializedName("username") val username: String,
    @SerializedName("id") val userId: String,
    @SerializedName("discriminator") val discriminator: String?,
    @SerializedName("avatar") val avatar: String?,
) {
    companion object {
        fun List<GuildUser>.getFilteredOrNull(): List<GuildUser>? =
                if (this.size == SingletonConnection.totalUsefulMembers) this
                else null
    }
}
