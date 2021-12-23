package com

import com.websocketsclient.api.outdoor.discordgateway.model.guildmembers.GuildUser.Companion.logFinalResult
import com.websocketsclient.application.Constant.APPLICATION_LOGGER
import com.websocketsclient.application.connection.AsyncSingletonMemberRequest
import com.websocketsclient.application.connection.AsyncSingletonPingPong
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.*
import java.util.logging.Logger


@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val applicationLogger = Logger.getLogger(APPLICATION_LOGGER)

    val websocketsClient = HttpClient(CIO) {
        install(WebSockets)
    }

    coroutineScope {
        val deferredGuildUsers = async(coroutineContext) {
            AsyncSingletonMemberRequest.fetchUsersFromGuilds(websocketsClient, applicationLogger)
        }

        deferredGuildUsers.join()
        launch {
            applicationLogger.info("USERS LIST RECEIVED")
            deferredGuildUsers.await().logFinalResult(applicationLogger)
            AsyncSingletonPingPong.holdPingPong(websocketsClient, applicationLogger)
        }
    }

}

//private suspend fun stayAlive(client: HttpClient, log: Logger) {
//    try {
//        SingletonConnection.holdWebsocketsConnection(client, log)
//    } catch (ex: CancellationException) {
//        restart(log, client, COROUTINE_JOB_DEAD)
//    } catch (ex: ClosedReceiveChannelException) {
//        restart(log, client, WEBSOCKETS_CONNECTION_LOST)
//    }
//}
//
//private suspend fun restart(log: Logger, client: HttpClient, message: String) {
//    log.info(message)
//    SingletonConnection.interactions = 0
//    stayAlive(client, log)
//}
