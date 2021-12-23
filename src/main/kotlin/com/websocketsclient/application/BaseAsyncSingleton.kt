package com.websocketsclient.application

import com.websocketsclient.api.outdoor.discordgateway.OutdoorData
import com.websocketsclient.application.Constant
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import java.util.logging.Logger

abstract class BaseAsyncSingleton {
    open val interactions: Int = 0
    suspend fun DefaultClientWebSocketSession.textFromGateway(): String =
        (incoming.receive() as? Frame.Text)?.readText() ?: throw  Exception(Constant.NULL_MESSAGE_FROM_GATEWAY)

    abstract suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String? = null)
    abstract suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): OutdoorData
}