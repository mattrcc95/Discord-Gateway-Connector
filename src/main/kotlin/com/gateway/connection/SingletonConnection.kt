package com.gateway.connection

import com.gateway.Constant
import com.gateway.Constant.CHUNK_HEADER
import com.gateway.Constant.GATEWAY_URL
import com.gateway.Constant.NULL_RESPONSE_FROM_GATEWAY
import com.gateway.comunication.indoor.guildrequest.Op8
import com.gateway.comunication.indoor.guildrequest.RequestData
import com.gateway.comunication.indoor.identify.Op2
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.outdoor.hello.Op10.Companion.receiveOp10Heartbeat
import com.gateway.comunication.outdoor.member.GuildMemberChunk.Companion.parseGuildUsers
import com.gateway.comunication.outdoor.member.GuildUser
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.io.File
import java.util.logging.Logger

object SingletonConnection {
    val guildFile = File("src/main/resources/guilds.txt")
    var interactions: Int = 0
    var memberList = mutableListOf<GuildUser>()
    var messageIsNotSent: Boolean = true

    suspend fun holdWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = GATEWAY_URL) {
            this.receiveOp10Heartbeat(log)?.let { gatewayResponse ->
//                guildFile
//                    .useLines { it.toList() }
//                    .forEach { guildId ->
                while (messageIsNotSent) {
                    delay(1000L)
                    sendToGateway(log, guildId = guildFile.useLines { it.toList() }[0])
                    receiveFromGateway(log)
                }
//                    }
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger) {
        (incoming.receive() as? Frame.Text)?.readText()?.let { response ->
            when (response.contains(CHUNK_HEADER)) {
                true -> {
                    parseGuildUsers(response)
                        .onEach { log.info(it.toString()) }
                        .also {
                            log.info("fetched: ${it.size} users in total")
                        }
                }
                false -> {log.info("${Constant.PONG_OK} --> $response")}
            }
        } ?: log.info(NULL_RESPONSE_FROM_GATEWAY)
    }

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String): Unit =
        when (interactions) {
            1 -> {
                val op2Identification = Gson().toJson(Op2())
                send(op2Identification)
                interactions++
                log.info("sent: $op2Identification")
            }
            2 -> {
                val op8GuildRequest = Gson().toJson(Op8(data = RequestData(guildId)))
                send(op8GuildRequest)
                interactions++
                log.info("sent: $op8GuildRequest")
            }
            else -> {
                val op1 = Gson().toJson(Op1(d = ++interactions))
                send(op1)
                log.info("sent: $op1")
            }
        }

}
