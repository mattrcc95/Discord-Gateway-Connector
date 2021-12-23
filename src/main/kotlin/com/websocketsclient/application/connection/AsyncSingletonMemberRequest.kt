package com.websocketsclient.application.connection

import com.Utils.alphabet
import com.Utils.getRepresentativeCharOrRandom
import com.Utils.guildFile
import com.Utils.totalGuilds
import com.google.gson.Gson
import com.websocketsclient.api.indoor.discordgateway.IndoorOp1
import com.websocketsclient.api.indoor.discordgateway.guildrequest.Op8
import com.websocketsclient.api.indoor.discordgateway.guildrequest.RequestData
import com.websocketsclient.api.indoor.discordgateway.identify.Op2
import com.websocketsclient.api.outdoor.discordgateway.FromGatewayOutdoorParser.toGuildMemberChunk
import com.websocketsclient.api.outdoor.discordgateway.FromGatewayOutdoorParser.toHeartbeat
import com.websocketsclient.api.outdoor.discordgateway.FromGatewayOutdoorParser.toOp11OrNothing
import com.websocketsclient.api.outdoor.discordgateway.OutdoorData
import com.websocketsclient.api.outdoor.discordgateway.model.guildmembers.GuildUser
import com.websocketsclient.application.BaseAsyncSingleton
import com.websocketsclient.application.Constant.CHUNK_HEADER
import com.websocketsclient.application.Constant.GATEWAY_URL
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import java.util.logging.Logger

object AsyncSingletonMemberRequest : BaseAsyncSingleton() {
    override var interactions = 0
    private var membersList = mutableListOf<GuildUser>()
    private var totalUsefulMembers = 0
    private var lettersProcessed = mutableSetOf<String>()
    private var guildsProcessed = 0

    suspend fun fetchUsersFromGuilds(client: HttpClient, log: Logger): List<GuildUser> {
        client.webSocket(urlString = GATEWAY_URL) {
            textFromGateway().toHeartbeat().let {
                guildFile.useLines { it.toList() }.forEach { guildId ->
                    while (true) {
                        delay(1000L)
                        sendToGateway(log, guildId)
                        receiveFromGateway(log)
                        if (lettersProcessed.size == alphabet.split(",").size) {
                            guildsProcessed++
                            reset()
                            break
                        }
                    }
                }
            }
            if(guildsProcessed == totalGuilds) {
                this.cancel()
            }
        }
        return membersList
    }

    override suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): OutdoorData =
        textFromGateway().let { response ->
            return when (response.contains(CHUNK_HEADER)) {
                true -> {
                    response.toGuildMemberChunk().also { usersChunk ->
                        val usefulMembers = usersChunk.extractInterestingUsers()
                        val usefulGuildMembers = usefulMembers.map { it.first }
                        totalUsefulMembers += usefulGuildMembers.size
                        lettersProcessed.add(getRepresentativeCharOrRandom(usefulMembers))
                        membersList.addAll(usefulGuildMembers)
                        log.info("debug processed Chars: $lettersProcessed")
                    }
                }
                false -> response.toOp11OrNothing(log)
            }
        }

    override suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String?) {
        when (interactions) {
            1 -> {
                if (guildsProcessed == 0) {
                    val op2Identification = Gson().toJson(Op2())
                    send(op2Identification)
                    log.info("debug sent: $op2Identification")
                }
                interactions++
            }
            2 -> {
                for (startsWith in alphabet.split(",")) {
                    val op8GuildRequest = Gson().toJson(
                        Op8(
                            data = RequestData(
                                guildId = guildId ?: throw Exception("guild id is null"),
                                query = startsWith
                            )
                        )
                    )
                    send(op8GuildRequest)
                    log.info("debug sent: $op8GuildRequest")
                }
                interactions++
            }
            else -> {
                val op1 = Gson().toJson(IndoorOp1(d = ++interactions))
                send(op1)
                log.info("debug sent: $op1")
            }
        }
    }

    private fun reset() {
        interactions = 0
        totalUsefulMembers = 0
        lettersProcessed = mutableSetOf()
    }
}
