package com.websocketsclient.application.connection

import com.websocketsclient.application.Constant.CHUNK_HEADER
import com.websocketsclient.application.Constant.GATEWAY_URL
import com.websocketsclient.application.Constant.NULL_MESSAGE_FROM_GATEWAY
import com.websocketsclient.api.indoor.gateway.guildrequest.Op8
import com.websocketsclient.api.indoor.gateway.guildrequest.RequestData
import com.websocketsclient.api.indoor.gateway.identify.Op2
import com.websocketsclient.api.indoor.gateway.IndoorOp1
import com.websocketsclient.api.outdoor.gateway.OutdoorData
import com.websocketsclient.api.outdoor.gateway.FromGatewayOutdoorParser.toGuildMemberChunk
import com.websocketsclient.api.outdoor.gateway.FromGatewayOutdoorParser.toHeartbeat
import com.websocketsclient.api.outdoor.gateway.FromGatewayOutdoorParser.toOp11OrNothing
import com.websocketsclient.api.outdoor.gateway.model.guildmembers.GuildUser
import com.websocketsclient.application.connection.Utils.alphabet
import com.websocketsclient.application.connection.Utils.getRepresentativeCharOrRandom
import com.websocketsclient.application.connection.Utils.guildFile
import com.websocketsclient.application.connection.Utils.guildsCount
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.util.logging.Logger

object SingletonConnection {
    private var interactions = 0
    private var membersList = mutableListOf<GuildUser>()
    private var messageIsNotSent = true
    private var totalUsefulMembers = 0
    private var guildsProcessed = 0
    private var processedChars = mutableSetOf<String>()

    suspend fun holdWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = GATEWAY_URL) {
            textFromGateway().toHeartbeat().let { heartbeat ->

                guildFile.useLines { it.toList() }.forEach { guildId ->
                    while (messageIsNotSent) {
                        delay(1000L)
                        sendToGateway(log, guildId = guildId)
                        receiveFromGateway(log)
                        if (processedChars.size == alphabet.split(",").size) {
                            membersList.also {
                                log.info("debug log list of useful: ")
                                it.forEach { log.info(it.toString()) }
                            }
                            messageIsNotSent = false
                            guildsProcessed++
                        }
                    }
                    if (guildsProcessed < guildsCount) {
                        log.info("debug guilds count not reached yet: $guildsProcessed < $guildsCount")
                        resetAll()
                    } else {
                        log.info("debug job done: closing websockets")
                        client.close()
                    }
                }

            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): OutdoorData =
        textFromGateway().let { response ->
            return when (response.contains(CHUNK_HEADER)) {
                true -> {
                    response.toGuildMemberChunk().also { usersChunk ->
                        usersChunk.also { it ->
                            val usefulMembers = it.extractInterestingUsers()
                            val usefulGuildMembers = usefulMembers.map { it.first }
                            totalUsefulMembers += usefulGuildMembers.size
                            processedChars.add(getRepresentativeCharOrRandom(usefulMembers))
                            membersList.addAll(usefulGuildMembers)
                            log.info("debug processed Chars: $processedChars")
                        }
                    }
                }
                false -> response.toOp11OrNothing(log)
            }
        }

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String) {
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
                    val op8GuildRequest = Gson().toJson(Op8(data = RequestData(guildId = guildId, query = startsWith)))
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

    private suspend fun DefaultClientWebSocketSession.textFromGateway(): String =
        (incoming.receive() as? Frame.Text)?.readText() ?: throw  Exception(NULL_MESSAGE_FROM_GATEWAY)

    private fun resetAll() {
        interactions = 0
        membersList = mutableListOf()
        messageIsNotSent = true
        totalUsefulMembers = 0
        processedChars = mutableSetOf()
    }
}
