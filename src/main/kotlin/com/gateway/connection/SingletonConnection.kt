package com.gateway.connection

import com.gateway.Constant.CHUNK_HEADER
import com.gateway.Constant.GATEWAY_URL
import com.gateway.Constant.NULL_MESSAGE_FROM_GATEWAY
import com.gateway.comunication.indoor.guildrequest.Op8
import com.gateway.comunication.indoor.guildrequest.RequestData
import com.gateway.comunication.indoor.identify.Op2
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.outdoor.OutdoorData
import com.gateway.comunication.outdoor.OutdoorParser
import com.gateway.comunication.outdoor.OutdoorParser.parseHeartbeat
import com.gateway.comunication.outdoor.OutdoorParser.parseOp11
import com.gateway.comunication.outdoor.guildmembers.GuildUser
import com.gateway.comunication.outdoor.guildmembers.GuildUser.Companion.getFilteredOrNull
import com.gateway.connection.Utils.START
import com.gateway.connection.Utils.alphabet
import com.gateway.connection.Utils.getRandomString
import com.gateway.connection.Utils.guildFile
import com.gateway.connection.Utils.guildsCount
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.util.logging.Logger

object SingletonConnection {
    var interactions = START
    var membersList = mutableListOf<GuildUser>()
    var messageIsNotSent: Boolean = true
    var totalUsefulMembers = START
    var totalMembers = START
    var guildsProcessed = START
    var processedChars = mutableSetOf<String>()

    suspend fun holdWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = GATEWAY_URL) {
            parseHeartbeat(textFromGateway()).let { heartbeat ->

                guildFile.useLines { it.toList() }.forEach { guildId ->
                    while (messageIsNotSent) {
                        log.info("in the while")
                        delay(1000L)
                        sendToGateway(log, guildId = guildId)
                        receiveFromGateway(log)
                        if (processedChars.size == alphabet.split(",").size) {
                            membersList.getFilteredOrNull()?.let {
                                log.info("log list of useful: ")
                                it.forEach { log.info(it.toString()) }
                            } ?: log.info("discrepancy found!")
                            messageIsNotSent = false
                            guildsProcessed++
                        }
                    }
                    if (guildsProcessed < guildsCount) {
                        log.info("guilds count not reached yet: $guildsProcessed < $guildsCount")
                        resetAll()
                    } else {
                        log.info("job done: closing websockets")
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
                    OutdoorParser.parseGuildUsers(response).also { usersChunk ->
                        usersChunk.also { it ->
                            totalMembers += it.chunk.data.members.size
                            val usefulMembers = it.extractInterestingUsers()
                            val usefulGuildMembers = usefulMembers.map { it.first }
                            totalUsefulMembers += usefulGuildMembers.size
                            processedChars.add(getRepresentativeCharOrRandom(usefulMembers))
                            membersList.addAll(usefulGuildMembers)
                            log.info("processed Chars: $processedChars")
                        }
                    }
                }
                false -> parseOp11(response, log)
            }
        }

    private fun getRepresentativeCharOrRandom(usefulMembers: List<Pair<GuildUser, String?>>): String =
        usefulMembers
            .firstOrNull { it.first.username[0] == (it.second?.get(0) ?: it.first.username[0]) }
            ?.let {
                it.first.username[0].toString()
            } ?: getRandomString()

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String) {
        when (interactions) {
            1 -> {
                if (guildsProcessed == START) {
                    val op2Identification = Gson().toJson(Op2())
                    send(op2Identification)
                    log.info("sent: $op2Identification")
                }
                interactions++
            }
            2 -> {
                for (startsWith in alphabet.split(",")) {
                    val op8GuildRequest = Gson().toJson(Op8(data = RequestData(guildId = guildId, query = startsWith)))
                    send(op8GuildRequest)
                    log.info("sent: $op8GuildRequest")
                }
                interactions++
            }
            else -> {
                val op1 = Gson().toJson(Op1(d = ++interactions))
                send(op1)
                log.info("sent: $op1")
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.textFromGateway(): String =
        (incoming.receive() as? Frame.Text)?.readText() ?: throw  Exception(NULL_MESSAGE_FROM_GATEWAY)

    private fun resetAll() {
        interactions = START
        membersList = mutableListOf()
        messageIsNotSent = true
        totalUsefulMembers = START
        totalMembers = START
        processedChars = mutableSetOf()
    }
}
