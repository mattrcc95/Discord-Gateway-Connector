package com.websocketsclient.api.outdoor.gateway

import com.websocketsclient.api.outdoor.gateway.model.guildmembers.ChunkData
import com.websocketsclient.api.outdoor.gateway.model.guildmembers.GuildUser
import com.websocketsclient.api.outdoor.gateway.model.hello.Heartbeat
import com.google.gson.annotations.SerializedName

sealed class OutdoorData {
    fun extractInterestingUsers(): List<Pair<GuildUser, String?>> =
        when (this) {
            is GuildMemberChunk -> {
                data
                    .members
                    .filter { it.roles.isEmpty() && it.hoistedRole.isNullOrBlank() }
                    .map { it.user to it.nick }
            }
            else -> emptyList()
        }
}

data class Op10(
    @SerializedName("op") val op: Int,
    @SerializedName("d") val d: Heartbeat,
    @SerializedName("s") val s: Int?,
    @SerializedName("t") val t: Int?,
) : OutdoorData()

data class GuildMemberChunk(
    @SerializedName("t") val t: String,
    @SerializedName("s") val s: Int,
    @SerializedName("op") val op: Int,
    @SerializedName("d") val data: ChunkData,
): OutdoorData()

object NotParsableYet : OutdoorData()

data class Op11(
    @SerializedName("op") val op: Int
) : OutdoorData()