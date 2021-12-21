package com.gateway.comunication.outdoor

import com.gateway.comunication.outdoor.guildmembers.GuildMemberChunk
import com.google.gson.Gson

object OutdoorParser {

    fun parseGuildUsers(response: String): GuildMemberChunkWithQueryPrefix {
        val users = Gson().fromJson(response, GuildMemberChunk::class.java)
        return GuildMemberChunkWithQueryPrefix(
            chunk = users.copy(
                data = users.data.copy(
                    members = users.data.members.filter { it.roles.isEmpty() && it.hoistedRole.isNullOrBlank() }
                )
            ),
            queryPrefix = "",
            totalChunksPerPrefix = users.data.chunkCount
        )
    }

    fun parseHeartbeat(response: String): Op10 = Gson().fromJson(response, Op10::class.java)
}