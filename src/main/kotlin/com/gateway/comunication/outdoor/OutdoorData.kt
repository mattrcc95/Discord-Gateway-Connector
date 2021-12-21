package com.gateway.comunication.outdoor

import com.gateway.comunication.outdoor.guildmembers.GuildMemberChunk
import com.gateway.comunication.outdoor.guildmembers.GuildUser
import com.gateway.comunication.outdoor.hello.Heartbeat
import com.google.gson.annotations.SerializedName

sealed class OutdoorData {
    fun extractInterestingUsers(): List<Pair<GuildUser, String?>> =
        when (this) {
            is GuildMemberChunkWithQueryPrefix -> {
                chunk.data
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

data class GuildMemberChunkWithQueryPrefix(
    val chunk: GuildMemberChunk,
    val queryPrefix: String,
    val totalChunksPerPrefix: Int?,
) : OutdoorData()

object NotParsableYet : OutdoorData()

data class Op11(
    @SerializedName("op") val op: Int
) : OutdoorData()