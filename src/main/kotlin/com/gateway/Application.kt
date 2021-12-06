package com.gateway

import com.gateway.Constant.COROUTINE_JOB_DEAD
import com.gateway.Constant.GATEWAY_CONNECTOR
import com.gateway.Constant.GATEWAY_INITIALIZATION_FAILED
import com.gateway.Constant.GATEWAY_URL
import com.gateway.Constant.HEARTBEAT_OK
import com.gateway.Constant.PING_OK
import com.gateway.Constant.PONG_OK
import com.gateway.Constant.WEBSOCKETS_CONNECTION_LOST
import com.gateway.exception.GatewayInitializerException
import com.gateway.model.initializer.init.GatewayInitializer
import com.gateway.model.initializer.pingpong.Ping
import com.gateway.model.initializer.pingpong.Pong
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.time.ZonedDateTime
import java.util.logging.Logger


@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val log = Logger.getLogger(GATEWAY_CONNECTOR)
    val client = HttpClient(CIO) {
        install(WebSockets)
    }

    coroutineScope {
        launch(Dispatchers.IO) {
            stayAlive(client, log)
        }
    }

}

private suspend fun stayAlive(client: HttpClient, log: Logger) {
    try {
        handleWebsocketCommunication(client, log, 0)
    } catch (ex: CancellationException) {
        log.info("$COROUTINE_JOB_DEAD + ${Thread.currentThread().name} + stack: ${Thread.currentThread().stackTrace.size}")
        stayAlive(client, log)
    } catch (ex: ClosedReceiveChannelException) {
        log.info("$WEBSOCKETS_CONNECTION_LOST + ${Thread.currentThread().name}")
        stayAlive(client, log)
    }
}

private suspend fun handleWebsocketCommunication(client: HttpClient, log: Logger, count: Int) {
    var count1 = count
    client.webSocket(urlString = GATEWAY_URL) {
        this.getHeartBeat(log)?.let { gatewayResponse ->
            while (true) {
                delay(gatewayResponse.d.heartbeatInterval)
                val jsonPing = Gson().toJson(Ping(d = count1++))
                send(jsonPing)
                log.info("$PING_OK --> $jsonPing  " +
                        Thread.currentThread().name +
                        " stack: ${Thread.currentThread().stackTrace.size}"
                )
                getPong(log)
            }
        } ?: throw GatewayInitializerException().also {
            log.info("$GATEWAY_INITIALIZATION_FAILED  " +
                    Thread.currentThread().name +
                    " stack: ${Thread.currentThread().stackTrace.size}"
            )
            client.close()
        }
    }
    client.close()
}

private suspend fun DefaultClientWebSocketSession.getHeartBeat(log: Logger): GatewayInitializer? =
    (incoming.receive() as? Frame.Text)?.readText()?.let { gatewayHeartBeat ->
        Gson().fromJson(gatewayHeartBeat, GatewayInitializer::class.java)?.also {
            log.info("$HEARTBEAT_OK${ZonedDateTime.now()} --> " +
                    "${it.d.heartbeatInterval}" +
                    Thread.currentThread().name +
                    " stack: ${Thread.currentThread().stackTrace.size}")
        }
    }

private suspend fun DefaultClientWebSocketSession.getPong(log: Logger): Pong? =
    (incoming.receive() as? Frame.Text)?.readText()?.let { pong ->
        Gson().fromJson(pong, Pong::class.java)?.also {
            log.info("$PONG_OK${ZonedDateTime.now()} " + "--> ${it.op}  + " +
                    Thread.currentThread().name +
                    " stack: ${Thread.currentThread().stackTrace.size}"
            )
        }
    }