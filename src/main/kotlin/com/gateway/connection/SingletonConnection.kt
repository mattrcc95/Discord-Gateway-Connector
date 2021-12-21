package com.gateway.connection

import com.gateway.Constant.CHUNK_HEADER
import com.gateway.Constant.GATEWAY_URL
import com.gateway.comunication.indoor.guildrequest.Op8
import com.gateway.comunication.indoor.guildrequest.RequestData
import com.gateway.comunication.indoor.identify.Op2
import com.gateway.comunication.inoutdoor.Op1
import com.gateway.comunication.outdoor.OutdoorData
import com.gateway.comunication.outdoor.OutdoorParser
import com.gateway.comunication.outdoor.OutdoorParser.parseHeartbeat
import com.gateway.comunication.outdoor.guildmembers.GuildUser
import com.gateway.comunication.outdoor.guildmembers.blmodel.ChunkUsersInfo
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.io.File
import java.util.logging.Logger

object SingletonConnection {
    const val alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"
    val guildFile = File("src/main/resources/guilds.txt")
    var interactions: Int = 0
    var guildChunks = 0
    var memberSet = mutableSetOf<GuildUser>()
    var messageIsNotSent: Boolean = true

    suspend fun holdWebsocketsConnection(client: HttpClient, log: Logger) {
        client.webSocket(urlString = GATEWAY_URL) {
            parseHeartbeat(textFromGateway())
                .let { heartbeat ->
                    while (messageIsNotSent) {
                        delay(heartbeat.d.heartbeatInterval)
                        sendToGateway(log, guildId = guildFile.useLines { it.toList() }[0])
                        receiveFromGateway().fillUsers()
                    }
                }
        }
    }

    private fun Pair<GatewayOutdoor?, OutdoorType>.fillUsers() {
        if (this.second == GUILD_USERS) {
            (this.first as ChunkUsersInfo).let { usersChunk ->
                memberSet.addAll(usersChunk.data)
                guildChunks += usersChunk.totalChunks
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.receiveFromGateway(): OutdoorData? =
        textFromGateway().let { response ->
            return when (response.contains(CHUNK_HEADER)) {
                true -> {
                    OutdoorParser.parseGuildUsers(response).also { usersChunk ->
                        usersChunk
                    }
                }
                false -> null
            }
        }

    private suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String) {
        when (interactions) {
            1 -> {
                val op2Identification = Gson().toJson(Op2())
                send(op2Identification)
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
        (incoming.receive() as? Frame.Text)?.readText()?.let {
            it
        } ?: throw  Exception("null string response from gateway")

}
