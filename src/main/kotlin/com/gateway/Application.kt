package com.gateway

import com.gateway.Constant.COROUTINE_JOB_DEAD
import com.gateway.Constant.GATEWAY_CONNECTOR
import com.gateway.Constant.WEBSOCKETS_CONNECTION_LOST
import com.gateway.comunication.indoor.Op11
import com.gateway.comunication.indoor.hello.Op10
import com.gateway.connection.ConnectionHandler
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
        ConnectionHandler.handleWebsocketsConnection(client, log)
    } catch (ex: CancellationException) {
        log.info("$COROUTINE_JOB_DEAD + ${Thread.currentThread().name} + stack: ${Thread.currentThread().stackTrace.size}")
        stayAlive(client, log)
    } catch (ex: ClosedReceiveChannelException) {
        log.info("$WEBSOCKETS_CONNECTION_LOST + ${Thread.currentThread().name}")
        stayAlive(client, log)
    }
}
