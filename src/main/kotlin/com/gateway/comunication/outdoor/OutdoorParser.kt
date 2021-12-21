package com.gateway.comunication.outdoor

import com.gateway.Constant.NOT_PARSABLE
import com.gateway.comunication.outdoor.guildmembers.GuildMemberChunk
import com.google.gson.Gson
import java.util.logging.Logger

object OutdoorParser {

    fun parseGuildUsers(response: String): GuildMemberChunkWithQueryPrefix {
        val users = Gson().fromJson(response, GuildMemberChunk::class.java)
        return GuildMemberChunkWithQueryPrefix(
            chunk = users,
            queryPrefix = "",
            totalChunksPerPrefix = users.data.chunkCount
        )
    }

    fun parseOp11(response: String, log: Logger): OutdoorData {
        return try {
            Gson().fromJson(response, Op11::class.java)
        } catch (ex: Exception) {
            NotParsableYet.also {
                log.info("$NOT_PARSABLE $response")
            }
        }
    }

    fun parseHeartbeat(response: String): Op10 = Gson().fromJson(response, Op10::class.java)
}