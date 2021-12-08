package com.gateway

import com.gateway.Constant.COROUTINE_JOB_DEAD
import com.gateway.Constant.GATEWAY_CONNECTOR
import com.gateway.Constant.WEBSOCKETS_CONNECTION_LOST
import com.gateway.connection.SingletonConnection
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
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
        SingletonConnection.handleWebsocketsConnection(client, log)
    } catch (ex: CancellationException) {
        log.info(COROUTINE_JOB_DEAD)
        stayAlive(client, log)
    } catch (ex: ClosedReceiveChannelException) {
        log.info(WEBSOCKETS_CONNECTION_LOST)
        stayAlive(client, log)
    }
}
