package com.websocketsclient.api.outdoor.discordgateway.model.guildmembers

import com.google.gson.annotations.SerializedName
import java.util.logging.Logger

data class GuildUser(
    @SerializedName("username") val username: String,
    @SerializedName("id") val userId: String,
    @SerializedName("discriminator") val discriminator: String?,
    @SerializedName("avatar") val avatar: String?,
) {
    companion object {
        suspend fun List<GuildUser>.logFinalResult(log: Logger) {
            log.info("FETCHED: ${this.size}")
            this.forEach {
                log.info(it.toString())
            }
        }
    }
}
