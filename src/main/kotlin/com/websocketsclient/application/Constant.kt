package com.websocketsclient.application

object Constant {
    const val GATEWAY_URL = "wss://gateway.discord.gg/?v=9&encoding=json"
    const val WEBSOCKETS_LOGGER = "websockets logger"
    const val HTTPCLIENT_LOGGER = "http client logger"
    const val APPLICATION_LOGGER = "application logger"
    const val CHUNK_HEADER = "GUILD_MEMBERS_CHUNK"
    const val NOT_PARSABLE = "not yet parsable object "
    const val NULL_MESSAGE_FROM_GATEWAY = "null string response from gateway"
    const val COROUTINE_JOB_DEAD = "coroutine job canceled, restarting..."
    const val WEBSOCKETS_CONNECTION_LOST = "gateway connection lost, restarting..."
}
