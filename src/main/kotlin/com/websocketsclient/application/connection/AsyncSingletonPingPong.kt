package com.websocketsclient.application.connection

import com.google.gson.Gson
import com.websocketsclient.api.indoor.discordgateway.IndoorOp1
import com.websocketsclient.api.outdoor.discordgateway.FromGatewayOutdoorParser.toHeartbeat
import com.websocketsclient.api.outdoor.discordgateway.FromGatewayOutdoorParser.toOp11OrNothing
import com.websocketsclient.api.outdoor.discordgateway.OutdoorData
import com.websocketsclient.application.BaseAsyncSingleton
import com.websocketsclient.application.Constant
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.delay
import java.util.logging.Logger

object AsyncSingletonPingPong : BaseAsyncSingleton() {
    override var interactions = 0

    suspend fun holdPingPong(client: HttpClient, log: Logger) {
        client.webSocket(urlString = Constant.GATEWAY_URL) {
            textFromGateway().toHeartbeat().let { heartbeat ->
                while (true) {
                    delay(heartbeat.d.heartbeatInterval)
                    sendToGateway(log)
                    receiveFromGateway(log)
                }
            }
        }
    }

    override suspend fun DefaultClientWebSocketSession.receiveFromGateway(log: Logger): OutdoorData =
        textFromGateway().toOp11OrNothing(log)

    override suspend fun DefaultClientWebSocketSession.sendToGateway(log: Logger, guildId: String?) {
        val op1 = Gson().toJson(IndoorOp1(d = ++interactions))
        send(op1)
        log.info("debug sent: $op1")
    }

}
